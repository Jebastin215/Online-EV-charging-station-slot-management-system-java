package com.evcharging.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/map")
public class MapServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String lat = req.getParameter("lat");
        String lon = req.getParameter("lon");
        req.setAttribute("msg", "");
        req.setAttribute("lat", lat);
        req.setAttribute("lon", lon);
        req.getRequestDispatcher("/jsp/map.jsp").forward(req, resp);
    }
}
