package edu.bbte.idde.szim2182;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/hikes")
public class Filter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);

        boolean isLoggedIn = session != null && session.getAttribute("isLoggedIn") != null;

        if (isLoggedIn) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect("Login.jsp");
        }
    }
}
