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
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/tariff")
public class TariffServlet extends HttpServlet {

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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ev_station WHERE uname=?");
            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Map<String, Object> data = new LinkedHashMap<>();
                var meta = rs.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    data.put(meta.getColumnLabel(i), rs.getObject(i));
                }
                req.setAttribute("data", data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("msg", "");
        req.setAttribute("uname", uname);
        req.getRequestDispatcher("/jsp/tariff.jsp").forward(req, resp);
    }
}
