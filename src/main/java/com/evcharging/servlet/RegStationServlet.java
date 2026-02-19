package com.evcharging.servlet;

import com.evcharging.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/reg_station")
public class RegStationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("msg", "");
        req.getRequestDispatcher("/jsp/reg_station.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String msg = "";
        String stype = req.getParameter("stype");
        String name = req.getParameter("name");
        String numCharger = req.getParameter("num_charger");
        String area = req.getParameter("area");
        String city = req.getParameter("city");
        String lat = req.getParameter("lat");
        String lon = req.getParameter("lon");
        String uname = req.getParameter("uname");
        String pass1 = req.getParameter("pass");
        String repass = req.getParameter("repass") != null ? req.getParameter("repass") : "";
        String landmark = req.getParameter("landmark");
        String mobile = req.getParameter("mobile");
        String email = req.getParameter("email");

        if (!pass1.equals(repass)) {
            msg = "Password mismatched";
            req.setAttribute("msg", msg);
            req.getRequestDispatcher("/jsp/reg_station.jsp").forward(req, resp);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO ev_station(name, stype, num_charger, area, city, lat, lon, uname, pass, landmark, mobile, email, distance) "
                            +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, stype);
            ps.setString(3, numCharger);
            ps.setString(4, area);
            ps.setString(5, city);
            ps.setString(6, lat);
            ps.setString(7, lon);
            ps.setString(8, uname);
            ps.setString(9, pass1);
            ps.setString(10, landmark);
            ps.setString(11, mobile);
            ps.setString(12, email);
            ps.setString(13, "3");
            ps.executeUpdate();

            // Get generated station ID
            ResultSet rsGen = ps.getGeneratedKeys();
            int maxId = 0;
            if (rsGen.next()) {
                maxId = rsGen.getInt(1);
            }

            // Create slots for the station
            int num = Integer.parseInt(numCharger);
            for (int i = 1; i <= num; i++) {
                PreparedStatement psSlot = conn.prepareStatement(
                        "INSERT INTO ev_slot(station, slot) VALUES (?, ?)");
                psSlot.setString(1, String.valueOf(maxId));
                psSlot.setInt(2, i);
                psSlot.executeUpdate();
            }

            msg = "success";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Registration failed.";
        }

        req.setAttribute("msg", msg);
        req.getRequestDispatcher("/jsp/reg_station.jsp").forward(req, resp);
    }
}
