package com.evcharging.servlet;

import com.evcharging.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/verify_otp")
public class VerifyOtpServlet extends HttpServlet {

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
        String msg = "";
        String rid = req.getParameter("rid");
        String sid = req.getParameter("sid");
        String key = ""; // Moved here for scope visibility

        try (Connection conn = DBConnection.getConnection()) {
            // Get user mobile
            long mobile = 0;
            try (PreparedStatement psUser = conn.prepareStatement("SELECT * FROM ev_register WHERE uname=?")) {
                psUser.setString(1, uname);
                try (ResultSet rsUser = psUser.executeQuery()) {
                    if (rsUser.next()) {
                        mobile = rsUser.getLong("mobile");
                    }
                }
            }

            // Get booking OTP & sms status
            if (rid != null && !rid.isEmpty()) {
                try (PreparedStatement psBook = conn.prepareStatement("SELECT * FROM ev_booking WHERE id=?")) {
                    psBook.setString(1, rid);
                    try (ResultSet rsBook = psBook.executeQuery()) {
                        if (rsBook.next()) {
                            key = rsBook.getString("otp");
                            int smsSt = rsBook.getInt("sms_st");

                            if (smsSt == 1) {
                                // Attempt to send SMS (best-effort)
                                try {
                                    String messEnc = URLEncoder.encode("Key: " + key, StandardCharsets.UTF_8);
                                    String smsUrl = "http://iotcloud.co.in/testsms/sms.php?sms=emr&name=User&mess="
                                            + messEnc
                                            + "&mobile=" + mobile;
                                    // Note: Blocking call, ideally should be async
                                    new URL(smsUrl).openStream().close();
                                } catch (Exception ignored) {
                                }

                                try (PreparedStatement psUpdSms = conn.prepareStatement(
                                        "UPDATE ev_booking SET sms_st=0 WHERE id=?")) {
                                    psUpdSms.setString(1, rid);
                                    psUpdSms.executeUpdate();
                                }
                            }
                        }
                    }
                }
            }

            // Verify OTP on POST
            if ("POST".equalsIgnoreCase(req.getMethod())) {
                String otp = req.getParameter("otp");
                if (key != null && key.equals(otp) && !key.isEmpty()) {
                    try (PreparedStatement psPay = conn.prepareStatement(
                            "UPDATE ev_booking SET pay_st=2, status=3 WHERE id=?")) {
                        psPay.setString(1, rid);
                        psPay.executeUpdate();
                    }
                    msg = "Amount Paid Successfully";
                } else {
                    msg = "Invalid OTP";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("rid", rid);
        req.setAttribute("sid", sid);
        req.setAttribute("msg", msg);
        req.setAttribute("otp_dev", key); // Dev Mode: Expose OTP
        req.getRequestDispatcher("/jsp/verify_otp.jsp").forward(req, resp);
    }
}
