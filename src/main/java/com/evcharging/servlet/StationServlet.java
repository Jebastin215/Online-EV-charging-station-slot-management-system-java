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

import static java.lang.Math.*;

@WebServlet("/station")
public class StationServlet extends HttpServlet {

    private static final double R = 6373.0; // Earth radius in km

    private Double toDouble(Object value) {
        if (value == null) return null;
        try {
            return Double.parseDouble(value.toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

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
        String st = "";
        List<List<Object>> data = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            // Get user location
            PreparedStatement psUser = conn.prepareStatement("SELECT * FROM ev_register WHERE uname=?");
            psUser.setString(1, uname);
            ResultSet rsUser = psUser.executeQuery();
            Double lat2 = null, lon2 = null;
            if (rsUser.next()) {
                lat2 = toDouble(rsUser.getObject("latitude"));
                lon2 = toDouble(rsUser.getObject("longitude"));
            }

            if (lat2 == null || lon2 == null) {
                msg = "Location not available. Please re-login and allow location access.";
            }

            LocalDateTime now = LocalDateTime.now();
            String rdate = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String rdate2 = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String[] cd1 = rdate.split("-");

            // Update station distances
            if (lat2 != null && lon2 != null) {
                PreparedStatement psAll = conn.prepareStatement("SELECT * FROM ev_station");
                ResultSet rsAll = psAll.executeQuery();
                while (rsAll.next()) {
                    Double lat1 = toDouble(rsAll.getObject("lat"));
                    Double lon1 = toDouble(rsAll.getObject("lon"));
                    if (lat1 == null || lon1 == null) continue;

                    double dlon = lon2 - lon1;
                    double dlat = lat2 - lat1;
                    double a = pow(sin(dlat / 2), 2) + cos(lat1) * cos(lat2) * pow(sin(dlon / 2), 2);
                    double c = 2 * atan2(sqrt(a), sqrt(1 - a));
                    double distance = R * c;
                    String ddv = String.valueOf(distance).substring(0, Math.min(2, String.valueOf(distance).length()));

                    PreparedStatement psUpd = conn.prepareStatement("UPDATE ev_station SET distance=? WHERE id=?");
                    psUpd.setString(1, ddv);
                    psUpd.setInt(2, rsAll.getInt("id"));
                    psUpd.executeUpdate();
                }
            }

            // Handle search or default listing
            String rdx = "";
            if ("POST".equalsIgnoreCase(req.getMethod())) {
                String getval = req.getParameter("getval");
                String rdate1 = req.getParameter("rdate1");
                String prd = "%" + getval + "%";
                String[] rdd = rdate1.split("-");
                rdx = rdd[2] + "-" + rdd[1] + "-" + rdd[0];

                LocalDate d0 = LocalDate.of(Integer.parseInt(cd1[2]), Integer.parseInt(cd1[1]), Integer.parseInt(cd1[0]));
                LocalDate d1 = LocalDate.of(Integer.parseInt(rdd[0]), Integer.parseInt(rdd[1]), Integer.parseInt(rdd[2]));
                long dy = java.time.temporal.ChronoUnit.DAYS.between(d0, d1);

                PreparedStatement psCount = conn.prepareStatement(
                        "SELECT COUNT(*) FROM ev_station WHERE area LIKE ? OR city LIKE ? OR landmark LIKE ?");
                psCount.setString(1, prd);
                psCount.setString(2, prd);
                psCount.setString(3, prd);
                ResultSet rsCount = psCount.executeQuery();
                rsCount.next();
                int dn = rsCount.getInt(1);

                if (dn > 0 && dy >= 0) {
                    st = "1";
                    PreparedStatement psSearch = conn.prepareStatement(
                            "SELECT * FROM ev_station WHERE area LIKE ? OR city LIKE ? OR landmark LIKE ? ORDER BY distance");
                    psSearch.setString(1, prd);
                    psSearch.setString(2, prd);
                    psSearch.setString(3, prd);
                    ResultSet rsSearch = psSearch.executeQuery();
                    buildStationData(rsSearch, data, conn, rdx, lat2, lon2);
                } else {
                    st = "2";
                }
            } else {
                st = "1";
                PreparedStatement psAll = conn.prepareStatement("SELECT * FROM ev_station ORDER BY distance");
                ResultSet rsAll = psAll.executeQuery();
                buildStationData(rsAll, data, conn, rdx, lat2, lon2);
            }

            req.setAttribute("rdate2", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Error loading stations.";
        }

        req.setAttribute("msg", msg);
        req.setAttribute("data", data);
        req.setAttribute("uname", uname);
        req.setAttribute("st", st);
        req.getRequestDispatcher("/jsp/station.jsp").forward(req, resp);
    }

    private void buildStationData(ResultSet rsSearch, List<List<Object>> data, Connection conn,
                                   String rdx, Double lat2, Double lon2) throws SQLException {
        while (rsSearch.next()) {
            List<Object> dt = new ArrayList<>();
            dt.add(rsSearch.getInt("id"));         // 0
            dt.add(rsSearch.getString("name"));    // 1
            dt.add(rsSearch.getString("stype"));   // 2
            dt.add(rsSearch.getInt("num_charger"));// 3
            dt.add(rsSearch.getString("area"));    // 4
            dt.add(rsSearch.getString("city"));    // 5
            dt.add(rsSearch.getString("lat"));     // 6
            dt.add(rsSearch.getString("lon"));     // 7
            dt.add(rsSearch.getString("uname"));   // 8
            dt.add(rsSearch.getString("pass"));    // 9
            dt.add(rsSearch.getInt("status"));     // 10
            dt.add(rsSearch.getString("landmark"));// 11
            dt.add(rsSearch.getLong("mobile"));     // 12
            dt.add(rsSearch.getString("email"));   // 13

            // Booking data for this station
            List<Object> dt2 = new ArrayList<>();
            String ss = "no";
            PreparedStatement psBookCount = conn.prepareStatement(
                    "SELECT COUNT(*) FROM ev_booking WHERE station=? AND rdate=? AND status=1");
            psBookCount.setInt(1, rsSearch.getInt("id"));
            psBookCount.setString(2, rdx);
            ResultSet rsBookCount = psBookCount.executeQuery();
            rsBookCount.next();
            int d4 = rsBookCount.getInt(1);
            if (d4 > 0) {
                ss = "yes";
            }

            dt.add(dt2);     // 14
            dt.add(ss);      // 15
            dt.add(rsSearch.getDouble("distance")); // 16
            data.add(dt);
        }
    }
}
