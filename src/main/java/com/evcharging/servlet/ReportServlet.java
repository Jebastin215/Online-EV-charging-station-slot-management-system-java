package com.evcharging.servlet;

import com.evcharging.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login2");
            return;
        }
        String uname = (String) session.getAttribute("username");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement psStation = conn.prepareStatement("SELECT * FROM ev_station WHERE uname=?");
            psStation.setString(1, uname);
            ResultSet rsStation = psStation.executeQuery();
            if (rsStation.next()) {
                int sid = rsStation.getInt("id");

                PreparedStatement ps = conn.prepareStatement("SELECT * FROM ev_booking WHERE station=?");
                ps.setInt(1, sid);
                ResultSet rs = ps.executeQuery();
                List<Map<String, Object>> data = new ArrayList<>();
                var meta = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        row.put(meta.getColumnLabel(i), rs.getObject(i));
                    }
                    data.add(row);
                }
                req.setAttribute("data", data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("msg", "");
        req.setAttribute("uname", uname);
        req.getRequestDispatcher("/jsp/report.jsp").forward(req, resp);
    }
}
