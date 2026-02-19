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

@WebServlet("/login_admin")
public class LoginAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("msg", "");
        req.getRequestDispatcher("/jsp/login_admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String msg = "";
        String uname = (req.getParameter("uname") != null) ? req.getParameter("uname").trim() : "";
        String pwd = (req.getParameter("pass") != null) ? req.getParameter("pass").trim() : "";

        if (uname.isEmpty() || pwd.isEmpty()) {
            msg = "Please enter username and password.";
        } else {
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(
                        "SELECT username FROM ev_admin WHERE LOWER(TRIM(username)) = LOWER(?) AND TRIM(password) = ? LIMIT 1");
                ps.setString(1, uname);
                ps.setString(2, pwd);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    HttpSession session = req.getSession();
                    session.setAttribute("username", uname);
                    resp.sendRedirect("admin");
                    return;
                } else {
                    // Check if any admin exists
                    PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) FROM ev_admin");
                    ResultSet rs2 = ps2.executeQuery();
                    rs2.next();
                    int adminCount = rs2.getInt(1);

                    if (adminCount == 0) {
                        msg = "Admin account not found in database. Import database/ev_charging.sql.";
                    } else {
                        PreparedStatement ps3 = conn.prepareStatement(
                                "SELECT 1 FROM ev_admin WHERE LOWER(TRIM(username)) = LOWER(?) LIMIT 1");
                        ps3.setString(1, uname);
                        ResultSet rs3 = ps3.executeQuery();
                        if (rs3.next()) {
                            msg = "Password is incorrect.";
                        } else {
                            msg = "Username not found.";
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msg = "Database error.";
            }
        }

        req.setAttribute("msg", msg);
        req.getRequestDispatcher("/jsp/login_admin.jsp").forward(req, resp);
    }
}
