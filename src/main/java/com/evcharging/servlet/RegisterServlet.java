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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("msg", "");
        req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String msg = "";
        String address = req.getParameter("address");
        String name = req.getParameter("name");
        String mobile = req.getParameter("mobile");
        String email = req.getParameter("email");
        String account = req.getParameter("account");
        String card = req.getParameter("card");
        String bank = req.getParameter("bank");
        String uname = req.getParameter("uname");
        String pass1 = req.getParameter("pass");
        String repass = req.getParameter("repass") != null ? req.getParameter("repass") : "";

        if (!pass1.equals(repass)) {
            msg = "Password mismatched";
            req.setAttribute("msg", msg);
            req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO ev_register(name, address, mobile, email, account, card, bank, amount, uname, pass) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, mobile);
            ps.setString(4, email);
            ps.setString(5, account);
            ps.setString(6, card);
            ps.setString(7, bank);
            ps.setString(8, "10000");
            ps.setString(9, uname);
            ps.setString(10, pass1);
            ps.executeUpdate();

            resp.sendRedirect("login");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Registration failed. Please try again.";
        }

        req.setAttribute("msg", msg);
        req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
    }
}
