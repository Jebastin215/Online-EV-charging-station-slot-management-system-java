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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("msg", "");
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String msg = "";
        String uname = req.getParameter("uname");
        String pwd = req.getParameter("pass");
        String lat = req.getParameter("lat");
        String lon = req.getParameter("lon");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM ev_register WHERE uname = ? AND pass = ?");
            ps.setString(1, uname);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("username", uname);

                PreparedStatement ps2 = conn.prepareStatement(
                        "UPDATE ev_register SET latitude=?, longitude=? WHERE uname=?");
                ps2.setString(1, lat);
                ps2.setString(2, lon);
                ps2.setString(3, uname);
                ps2.executeUpdate();

                resp.sendRedirect("userhome");
                return;
            } else {
                msg = "Incorrect username/password!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Database error.";
        }

        req.setAttribute("msg", msg);
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }
}
