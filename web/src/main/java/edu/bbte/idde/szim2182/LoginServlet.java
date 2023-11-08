package edu.bbte.idde.szim2182;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void init() {
        LOG.info("Initializing login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            // Login success
            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            // Redirect to a secure page
            resp.sendRedirect("hikes.jsp");
        } else {
            // Login failure
            req.setAttribute("errorMessage", "Invalid username or password");
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
        }
    }


}
