package com.evcharging.servlet;

import com.evcharging.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet("/book")
public class BookServlet extends HttpServlet {

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
        String sid = req.getParameter("sid");
        String slot = req.getParameter("slot");

        LocalDateTime now = LocalDateTime.now();
        String rdate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String cdate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String[] cd1 = cdate.split("-");

        // Build time array for dropdown
        List<String> tarr = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            tarr.add(String.valueOf(i));
        }

        try (Connection conn = DBConnection.getConnection()) {
            if ("POST".equalsIgnoreCase(req.getMethod())) {
                String carno = req.getParameter("carno");
                String reserve = req.getParameter("reserve");
                sid = req.getParameter("sid");
                slot = req.getParameter("slot");
                String bdate = req.getParameter("bdate");

                String t1 = req.getParameter("t1");
                String t2 = req.getParameter("t2");
                String t3 = req.getParameter("t3");
                String t4 = req.getParameter("t4");

                int sh, sm, eh, em;
                try {
                    sh = Integer.parseInt(t1);
                    sm = Integer.parseInt(t2);
                    eh = Integer.parseInt(t3);
                    em = Integer.parseInt(t4);
                } catch (NumberFormatException e) {
                    msg = "Invalid time selection.";
                    setAttributes(req, msg, uname, sid, slot, rdate, tarr, now);
                    req.getRequestDispatcher("/jsp/book.jsp").forward(req, resp);
                    return;
                }

                int startMinutes = sh * 60 + sm;
                int endMinutes = eh * 60 + em;

                if (endMinutes <= startMinutes) {
                    msg = "End time must be after start time.";
                    setAttributes(req, msg, uname, sid, slot, rdate, tarr, now);
                    req.getRequestDispatcher("/jsp/book.jsp").forward(req, resp);
                    return;
                }

                String btime1 = t1 + ":" + t2;
                String btime2 = t3 + ":" + t4;

                String[] bd1 = bdate.split("-");
                String bdate1 = bd1[2] + "-" + bd1[1] + "-" + bd1[0];

                LocalDate d0 = LocalDate.of(Integer.parseInt(cd1[0]), Integer.parseInt(cd1[1]),
                        Integer.parseInt(cd1[2]));
                LocalDate d1 = LocalDate.of(Integer.parseInt(bd1[0]), Integer.parseInt(bd1[1]),
                        Integer.parseInt(bd1[2]));
                long dy = java.time.temporal.ChronoUnit.DAYS.between(d0, d1);

                int y = 0;
                if (cdate.equals(bdate1)) {
                    int nowTotal = now.getHour() * 60 + now.getMinute();
                    int nowRounded = ((nowTotal + 4) / 5) * 5;
                    if (startMinutes < nowRounded) {
                        y++;
                    }
                }

                // Check existing bookings at same time
                int x = 0;
                PreparedStatement psCheck = conn.prepareStatement(
                        "SELECT * FROM ev_booking WHERE station=? AND rdate=? AND slot=? AND status=1");
                psCheck.setString(1, sid);
                psCheck.setString(2, bdate1);
                psCheck.setString(3, slot);
                ResultSet rsCheck = psCheck.executeQuery();
                while (rsCheck.next()) {
                    String[] th2 = rsCheck.getString("btime1").split(":");
                    if (th2[0].equals(t1)) {
                        x++;
                    }
                }

                if (x < 2 && y == 0 && dy >= 0) {
                    String rtime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                    String cimage = "evch.jpg";

                    PreparedStatement psInsert = conn.prepareStatement(
                            "INSERT INTO ev_booking(uname, station, carno, reserve, slot, cimage, rtime, rdate, status, btime1, btime2, charge_st) "
                                    +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    psInsert.setString(1, uname);
                    psInsert.setString(2, sid);
                    psInsert.setString(3, carno);
                    psInsert.setString(4, reserve);
                    psInsert.setString(5, slot);
                    psInsert.setString(6, cimage);
                    psInsert.setString(7, rtime);
                    psInsert.setString(8, bdate1);
                    psInsert.setString(9, "1");
                    psInsert.setString(10, btime1);
                    psInsert.setString(11, btime2);
                    psInsert.setInt(12, 1);
                    psInsert.executeUpdate();

                    resp.sendRedirect("slot?sid=" + sid);
                    return;
                } else {
                    if (dy < 0) {
                        msg = "Booking date must be today or later.";
                    } else if (y != 0) {
                        msg = "Start time must be after current time.";
                    } else {
                        msg = "fail";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Booking error.";
        }

        setAttributes(req, msg, uname, sid, slot, rdate, tarr, now);
        req.getRequestDispatcher("/jsp/book.jsp").forward(req, resp);
    }

    private void setAttributes(HttpServletRequest req, String msg, String uname, String sid,
            String slot, String rdate, List<String> tarr, LocalDateTime now) {
        req.setAttribute("msg", msg);
        req.setAttribute("uname", uname);
        req.setAttribute("sid", sid);
        req.setAttribute("slot", slot);
        req.setAttribute("rdate", rdate);
        req.setAttribute("tarr", tarr);
        req.setAttribute("now_h", now.getHour());
        req.setAttribute("now_m", now.getMinute());
    }
}
