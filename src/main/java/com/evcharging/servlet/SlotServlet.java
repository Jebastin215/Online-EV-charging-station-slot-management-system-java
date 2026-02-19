package com.evcharging.servlet;

import com.evcharging.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/slot")
public class SlotServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String uname = "";
        if (session != null && session.getAttribute("username") != null) {
            uname = (String) session.getAttribute("username");
        }

        String sid = req.getParameter("sid");
        String msg = "";
        String station = "";
        List<List<Object>> sdata = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String rdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            PreparedStatement psStation = conn.prepareStatement("SELECT * FROM ev_station WHERE id=?");
            psStation.setString(1, sid);
            ResultSet rsStation = psStation.executeQuery();
            if (rsStation.next()) {
                station = rsStation.getString("name");
            }

            // Get slots
            PreparedStatement psSlots = conn.prepareStatement("SELECT * FROM ev_slot WHERE station=?");
            psSlots.setString(1, sid);
            ResultSet rsSlots = psSlots.executeQuery();

            while (rsSlots.next()) {
                List<Object> dt = new ArrayList<>();
                int slotNum = rsSlots.getInt("slot");
                dt.add(slotNum); // 0 - slot number

                dt.add(""); // 1 - placeholder

                // Check booking status
                String sv = "no";
                List<Object> dd = new ArrayList<>();
                PreparedStatement psBookCount = conn.prepareStatement(
                        "SELECT COUNT(*) FROM ev_booking WHERE slot=? AND station=? AND status=1 AND rdate=?");
                psBookCount.setInt(1, slotNum);
                psBookCount.setString(2, sid);
                psBookCount.setString(3, rdate);
                ResultSet rsBookCount = psBookCount.executeQuery();
                rsBookCount.next();
                int cn = rsBookCount.getInt(1);

                if (cn > 0) {
                    PreparedStatement psBook = conn.prepareStatement(
                            "SELECT * FROM ev_booking WHERE slot=? AND station=? AND status=1 AND rdate=?");
                    psBook.setInt(1, slotNum);
                    psBook.setString(2, sid);
                    psBook.setString(3, rdate);
                    ResultSet rsBook = psBook.executeQuery();
                    if (rsBook.next()) {
                        if (rsBook.getInt("alert_st") == 5) {
                            sv = "yes";
                        }
                        // Store booking data
                        var meta = rsBook.getMetaData();
                        for (int i = 1; i <= meta.getColumnCount(); i++) {
                            dd.add(rsBook.getObject(i));
                        }
                    }
                }

                dt.add(sv); // 2 - booked status (alert_st == 5)
                dt.add(dd); // 3 - booking data
                dt.add(cn > 0 ? "booked" : "free"); // 4 - raw booking status
                sdata.add(dt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("msg", msg);
        req.setAttribute("uname", uname);
        req.setAttribute("sid", sid);
        req.setAttribute("station", station);
        req.setAttribute("act", "ok");
        req.setAttribute("sdata", sdata);
        req.getRequestDispatcher("/jsp/slot.jsp").forward(req, resp);
    }
}
