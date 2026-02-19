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

@WebServlet("/select")
public class SelectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sid = req.getParameter("sid");
        String rid = req.getParameter("rid");
        req.setAttribute("sid", sid);
        req.setAttribute("rid", rid);
        req.getRequestDispatcher("/jsp/select.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sid = req.getParameter("sid");
        String rid = req.getParameter("rid");
        String plan = req.getParameter("plan");

        if (sid == null) sid = req.getParameter("sid");
        if (rid == null) rid = req.getParameter("rid");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE ev_booking SET plan=?, charge_st=1, charge_min=0, charge_sec=0 WHERE id=?");
            ps.setString(1, plan);
            ps.setString(2, rid);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("slot?sid=" + sid);
    }
}
