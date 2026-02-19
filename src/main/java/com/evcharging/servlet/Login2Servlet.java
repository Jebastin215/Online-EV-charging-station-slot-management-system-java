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

@WebServlet("/login2")
public class Login2Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("msg", "");
        req.getRequestDispatcher("/jsp/login2.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String msg = "";
        String uname = req.getParameter("uname");
        String pwd = req.getParameter("pass");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM ev_station WHERE uname = ? AND pass = ? AND status=1");
            ps.setString(1, uname);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("username", uname);
                resp.sendRedirect("home");
                return;
            } else {
                msg = "Incorrect username/password! or Not Approved";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Database error.";
        }

        req.setAttribute("msg", msg);
        req.getRequestDispatcher("/jsp/login2.jsp").forward(req, resp);
    }
}
