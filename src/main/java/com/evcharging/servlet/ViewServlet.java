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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/view")
public class ViewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login2");
            return;
        }
        String uname = (String) session.getAttribute("username");
        String msg = "";
        String station = "";
        int sid = 0;
        int s1 = 0, s2 = 0, s3 = 0, s4 = 0, s5 = 0, s6 = 0, s7 = 0, s8 = 0, s9 = 0, s10 = 0;
        List<List<Object>> sdata = new ArrayList<>();
        List<Map<String, Object>> data = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            // Handle actions (pay, start)
            String act = req.getParameter("act");
            if ("pay".equals(act)) {
                String rid = req.getParameter("rid");
                PreparedStatement psPay = conn.prepareStatement(
                        "UPDATE ev_booking SET pay_st=2, status=3 WHERE id=?");
                psPay.setString(1, rid);
                psPay.executeUpdate();
                resp.sendRedirect("view");
                return;
            }
            if ("start".equals(act)) {
                String rid = req.getParameter("rid");
                PreparedStatement psStart = conn.prepareStatement(
                        "UPDATE ev_booking SET charge_st=2 WHERE id=?");
                psStart.setString(1, rid);
                psStart.executeUpdate();
                resp.sendRedirect("view");
                return;
            }

            // Get station info
            PreparedStatement psStation = conn.prepareStatement("SELECT * FROM ev_station WHERE uname=?");
            psStation.setString(1, uname);
            ResultSet rsStation = psStation.executeQuery();
            if (rsStation.next()) {
                sid = rsStation.getInt("id");
                station = rsStation.getString("name");
                int num = rsStation.getInt("num_charger");

                // Build slot data
                String rdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                for (int i = 1; i <= num; i++) {
                    List<Object> dt = new ArrayList<>();
                    PreparedStatement psCount = conn.prepareStatement(
                            "SELECT COUNT(*) FROM ev_booking WHERE slot=? AND station=? AND status=1 AND rdate=?");
                    psCount.setInt(1, i);
                    psCount.setInt(2, sid);
                    psCount.setString(3, rdate);
                    ResultSet rsCount = psCount.executeQuery();
                    rsCount.next();
                    int cn = rsCount.getInt(1);

                    List<Object> dd = new ArrayList<>();
                    if (cn > 0) {
                        dt.add("yes");
                        PreparedStatement psBooking = conn.prepareStatement(
                                "SELECT * FROM ev_booking WHERE slot=? AND station=? AND status=1 AND rdate=?");
                        psBooking.setInt(1, i);
                        psBooking.setInt(2, sid);
                        psBooking.setString(3, rdate);
                        ResultSet rsBooking = psBooking.executeQuery();
                        if (rsBooking.next()) {
                            var meta = rsBooking.getMetaData();
                            for (int j = 1; j <= meta.getColumnCount(); j++) {
                                dd.add(rsBooking.getObject(j));
                            }
                            // Debug log
                            System.out.println("Slot " + i + " charge_st (col 20): " + rsBooking.getObject(20));
                        }
                    } else {
                        dt.add("no");
                    }
                    dt.add(String.valueOf(i));
                    dt.add(dd);
                    sdata.add(dt);
                }
            }

            // Get active bookings for slot status indicators
            PreparedStatement psActive = conn.prepareStatement(
                    "SELECT * FROM ev_booking WHERE station=? AND status=1");
            psActive.setInt(1, sid);
            ResultSet rsActive = psActive.executeQuery();
            var meta = rsActive.getMetaData();
            while (rsActive.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    row.put(meta.getColumnLabel(i), rsActive.getObject(i));
                }
                data.add(row);

                int slotNum = rsActive.getInt("slot");
                switch (slotNum) {
                    case 1:
                        s1 = 1;
                        break;
                    case 2:
                        s2 = 2;
                        break;
                    case 3:
                        s3 = 3;
                        break;
                    case 4:
                        s4 = 4;
                        break;
                    case 5:
                        s5 = 5;
                        break;
                    case 6:
                        s6 = 6;
                        break;
                    case 7:
                        s7 = 7;
                        break;
                    case 8:
                        s8 = 8;
                        break;
                    case 9:
                        s9 = 9;
                        break;
                    case 10:
                        s10 = 10;
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("msg", msg);
        req.setAttribute("uname", uname);
        req.setAttribute("sid", sid);
        req.setAttribute("station", station);
        req.setAttribute("act", "ok");
        req.setAttribute("data", data);
        req.setAttribute("sdata", sdata);
        req.setAttribute("s1", s1);
        req.setAttribute("s2", s2);
        req.setAttribute("s3", s3);
        req.setAttribute("s4", s4);
        req.setAttribute("s5", s5);
        req.setAttribute("s6", s6);
        req.setAttribute("s7", s7);
        req.setAttribute("s8", s8);
        req.setAttribute("s9", s9);
        req.setAttribute("s10", s10);
        req.getRequestDispatcher("/jsp/view.jsp").forward(req, resp);
    }
}
