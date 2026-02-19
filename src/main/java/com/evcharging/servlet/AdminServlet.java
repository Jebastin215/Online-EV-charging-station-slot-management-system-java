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

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login_admin");
            return;
        }

        String msg = "";
        String email = "";
        String mess = "";
        String act = req.getParameter("act");

        try (Connection conn = DBConnection.getConnection()) {
            if ("yes".equals(act)) {
                String sid = req.getParameter("sid");
                PreparedStatement psStation = conn.prepareStatement("SELECT * FROM ev_station WHERE id=?");
                psStation.setString(1, sid);
                ResultSet rsStation = psStation.executeQuery();
                if (rsStation.next()) {
                    email = rsStation.getString("email");
                    mess = "EV Station Approved, Username: " + rsStation.getString("uname") +
                            ", Password:" + rsStation.getString("pass");
                }

                PreparedStatement psUpd = conn.prepareStatement("UPDATE ev_station SET status=1 WHERE id=?");
                psUpd.setString(1, sid);
                psUpd.executeUpdate();
                msg = "ok";
            }

            // Get all stations
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ev_station");
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("msg", msg);
        req.setAttribute("mess", mess);
        req.setAttribute("email", email);
        req.getRequestDispatcher("/jsp/admin.jsp").forward(req, resp);
    }
}
