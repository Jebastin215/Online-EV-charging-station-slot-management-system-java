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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login");
            return;
        }
        String uname = (String) session.getAttribute("username");
        String rid = req.getParameter("rid");
        String sid = req.getParameter("sid");
        double amount = 0;
        String card = "";

        try (Connection conn = DBConnection.getConnection()) {
            // Get user card info
            String queryUser = "SELECT * FROM ev_register WHERE uname=?";
            try (PreparedStatement psUser = conn.prepareStatement(queryUser)) {
                psUser.setString(1, uname);
                try (ResultSet rsUser = psUser.executeQuery()) {
                    if (rsUser.next()) {
                        card = rsUser.getString("card");
                    }
                }
            }

            // Get booking info
            String queryBook = "SELECT * FROM ev_booking WHERE id=?";
            try (PreparedStatement psBook = conn.prepareStatement(queryBook)) {
                psBook.setString(1, rid);
                try (ResultSet rsBook = psBook.executeQuery()) {
                    if (rsBook.next()) {
                        double ch = rsBook.getDouble("charge");
                        amount = ch > 0 ? ch : 20;
                    }
                }
            }

            // Update booking with current date/time and amount
            String rdate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String rtime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            String queryUpd = "UPDATE ev_booking SET edate=?, etime=?, amount=? WHERE id=?";
            try (PreparedStatement psUpd = conn.prepareStatement(queryUpd)) {
                psUpd.setString(1, rdate);
                psUpd.setString(2, rtime);
                psUpd.setDouble(3, amount);
                psUpd.setString(4, rid);
                psUpd.executeUpdate();
            }

            // Handle POST (payment submission)
            if ("POST".equalsIgnoreCase(req.getMethod())) {
                String payMode = req.getParameter("pay_mode");
                if ("Bank".equals(payMode)) {
                    Random rnd = new Random();
                    String otp = String.valueOf(rnd.nextInt(9000) + 1000);
                    String queryOtp = "UPDATE ev_booking SET pay_mode=?, sms_st=1, otp=? WHERE id=?";
                    try (PreparedStatement psOtp = conn.prepareStatement(queryOtp)) {
                        psOtp.setString(1, payMode);
                        psOtp.setString(2, otp);
                        psOtp.setString(3, rid);
                        psOtp.executeUpdate();
                    }

                    resp.sendRedirect("verify_otp?rid=" + rid + "&sid=" + sid);
                    return;
                } else {
                    String queryPay = "UPDATE ev_booking SET pay_mode=?, pay_st=1 WHERE id=?";
                    try (PreparedStatement psPay = conn.prepareStatement(queryPay)) {
                        psPay.setString(1, payMode);
                        psPay.setString(2, rid);
                        psPay.executeUpdate();
                    }

                    resp.sendRedirect("slot?sid=" + sid);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("sid", sid);
        req.setAttribute("rid", rid);
        req.setAttribute("uname", uname);
        req.setAttribute("amount", amount);
        req.setAttribute("card", card);
        req.getRequestDispatcher("/jsp/payment.jsp").forward(req, resp);
    }
}
