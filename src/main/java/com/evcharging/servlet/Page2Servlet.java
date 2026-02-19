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
import java.time.LocalTime;

@WebServlet("/page2")
public class Page2Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String msg = "";
        String act = req.getParameter("act");
        String rid2 = req.getParameter("rid2");
        String retime = "";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE ev_booking SET alert_st=7 WHERE id=?");
            ps.setString(1, rid2);
            ps.executeUpdate();

            LocalTime now = LocalTime.now();
            int rh = now.getHour() + 1;
            String rh1 = String.valueOf(rh);
            String btime1 = rh1 + ":00";
            String btime2 = rh1 + ":30";
            retime = btime1 + " - " + btime2;

            if ("ok".equals(act)) {
                PreparedStatement psUpd = conn.prepareStatement(
                        "UPDATE ev_booking SET btime1=?, btime2=?, alert_st=0, status=1 WHERE id=?");
                psUpd.setString(1, btime1);
                psUpd.setString(2, btime2);
                psUpd.setString(3, rid2);
                psUpd.executeUpdate();
                msg = "go";
            }

            if ("cancel".equals(act)) {
                PreparedStatement psDel = conn.prepareStatement("DELETE FROM ev_booking WHERE id=?");
                psDel.setString(1, rid2);
                psDel.executeUpdate();
                msg = "go";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("msg", msg);
        req.setAttribute("rid2", rid2);
        req.setAttribute("act", act);
        req.setAttribute("retime", retime);
        req.getRequestDispatcher("/jsp/page2.jsp").forward(req, resp);
    }
}
