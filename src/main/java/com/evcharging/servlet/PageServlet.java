package com.evcharging.servlet;

import com.evcharging.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@WebServlet("/page")
public class PageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String sid = req.getParameter("sid");
        String rid = req.getParameter("rid");
        String st = "";
        String mobile = "";
        String mess = "";
        String name = "";
        String msg1 = "";
        String msg2 = "";
        String vno = "";
        String rid2 = "";

        String rdate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        try (Connection conn = DBConnection.getConnection()) {
            // Check for new alerts
            PreparedStatement ps1 = conn.prepareStatement(
                    "SELECT * FROM ev_booking WHERE status=1 AND rdate=? AND alert_st=0");
            ps1.setString(1, rdate);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                int rid1 = rs1.getInt("id");
                vno = rs1.getString("carno");
                int slotNum = rs1.getInt("slot");

                PreparedStatement psUser = conn.prepareStatement("SELECT * FROM ev_register WHERE uname=?");
                psUser.setString(1, rs1.getString("uname"));
                ResultSet rsUser = psUser.executeQuery();
                if (rsUser.next()) {
                    name = rsUser.getString("name");
                    mobile = String.valueOf(rsUser.getLong("mobile"));
                }
                st = "1";
                mess = "EV Charging Management System: Charging session started. Vehicle " + vno + ", Slot " + slotNum
                        + ".";

                PreparedStatement psUpd = conn.prepareStatement(
                        "UPDATE ev_booking SET alert_st=1 WHERE status=1 AND rdate=? AND alert_st=0 AND id=?");
                psUpd.setString(1, rdate);
                psUpd.setInt(2, rid1);
                psUpd.executeUpdate();
            }

            // Check ongoing alerts
            PreparedStatement ps2 = conn.prepareStatement(
                    "SELECT * FROM ev_booking WHERE status=1 AND rdate=? AND alert_st>=1");
            ps2.setString(1, rdate);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                int at = rs2.getInt("alert_st");
                int rid1 = rs2.getInt("id");
                rid2 = String.valueOf(rid1);
                vno = rs2.getString("carno");

                if (at == 1) {
                    PreparedStatement psUser = conn.prepareStatement("SELECT * FROM ev_register WHERE uname=?");
                    psUser.setString(1, rs2.getString("uname"));
                    ResultSet rsUser = psUser.executeQuery();
                    if (rsUser.next()) {
                        name = rsUser.getString("name");
                        mobile = String.valueOf(rsUser.getLong("mobile"));
                    }
                    msg2 = "2";
                    int slotNum = rs2.getInt("slot");
                    mess = "EV Charging Management System: Charging session started. Vehicle " + vno + ", Slot "
                            + slotNum + ".";
                }

                if (at < 5) {
                    PreparedStatement psUpd2 = conn.prepareStatement(
                            "UPDATE ev_booking SET alert_st=alert_st+1 WHERE status=1 AND rdate=? AND alert_st>0 AND id=?");
                    psUpd2.setString(1, rdate);
                    psUpd2.setInt(2, rid1);
                    psUpd2.executeUpdate();
                    msg1 = String.valueOf(at);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String nameQ = URLEncoder.encode(name != null ? name : "", StandardCharsets.UTF_8);
        String messQ = URLEncoder.encode(mess != null ? mess : "", StandardCharsets.UTF_8);

        req.setAttribute("sid", sid);
        req.setAttribute("rid", rid);
        req.setAttribute("st", st);
        req.setAttribute("mobile", mobile);
        req.setAttribute("mess", mess);
        req.setAttribute("name", name);
        req.setAttribute("name_q", nameQ);
        req.setAttribute("mess_q", messQ);
        req.setAttribute("msg1", msg1);
        req.setAttribute("vno", vno);
        req.setAttribute("rid2", rid2);
        req.setAttribute("msg2", msg2);
        req.getRequestDispatcher("/jsp/page.jsp").forward(req, resp);
    }
}
