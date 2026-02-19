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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String msg = "";
        String sid = req.getParameter("sid");

        try (Connection conn = DBConnection.getConnection()) {
            if (sid != null && !sid.isEmpty()) {
                PreparedStatement psDel = conn.prepareStatement("DELETE FROM ev_station WHERE id=?");
                psDel.setString(1, sid);
                psDel.executeUpdate();
                msg = "Record with ID " + sid + " was deleted.";
            }

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ev_station");
            ResultSet rs = ps.executeQuery();
            List<Map<String, Object>> data = new ArrayList<>();
            var meta = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    row.put(meta.getColumnLabel(i), rs.getObject(i));
                }
                data.add(row);
            }
            req.setAttribute("data", data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("msg", msg);
        req.setAttribute("mess", "");
        req.setAttribute("email", "");
        req.getRequestDispatcher("/jsp/admin.jsp").forward(req, resp);
    }
}
