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
import java.sql.ResultSet;

@WebServlet("/charge1")
public class Charge1Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String rid = req.getParameter("rid");
        int cmin = 0, csec = 0;

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ev_booking WHERE id=?");
            ps.setString(1, rid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cmin = rs.getInt("charge_min");
                csec = rs.getInt("charge_sec");
                int plan = rs.getInt("plan");
                int chargeSt = rs.getInt("charge_st");

                int cost = 0;
                if (plan == 1)
                    cost = 100;
                else if (plan == 2)
                    cost = 200;
                else
                    cost = 300;

                if (csec < 60) {
                    csec++;
                    PreparedStatement psUpd = conn.prepareStatement(
                            "UPDATE ev_booking SET charge_min=?, charge_sec=? WHERE id=?");
                    psUpd.setInt(1, cmin);
                    psUpd.setInt(2, csec);
                    psUpd.setString(3, rid);
                    psUpd.executeUpdate();
                } else {
                    PreparedStatement psUpd = conn.prepareStatement(
                            "UPDATE ev_booking SET charge_st=3, charge_time=30, charge_min=?, charge_sec=? WHERE id=?");
                    psUpd.setInt(1, cmin);
                    psUpd.setInt(2, csec);
                    psUpd.setString(3, rid);
                    psUpd.executeUpdate();
                }

                if (chargeSt == 3) {
                    PreparedStatement psUpd = conn.prepareStatement(
                            "UPDATE ev_booking SET charge_st=4, charge=? WHERE id=?");
                    psUpd.setInt(1, cost);
                    psUpd.setString(2, rid);
                    psUpd.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("rid", rid);
        req.setAttribute("cmin", cmin);
        req.setAttribute("csec", csec);
        req.getRequestDispatcher("/jsp/charge1.jsp").forward(req, resp);
    }
}
