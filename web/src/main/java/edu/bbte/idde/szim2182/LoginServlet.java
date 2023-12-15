package edu.bbte.idde.szim2182;

import edu.bbte.idde.szim2182.backend.datasource.ApplicationConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private ApplicationConfig config;

    @Override
    public void init() throws ServletException {
        super.init();
        log.info("Initializing LoginServlet");
        this.config = (ApplicationConfig) getServletContext().getAttribute("config");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (config == null || config.getLoginConfig() == null) {
            log.error("Login configuration is not available");
            req.setAttribute("errorMessage", "Login service is currently unavailable");
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
            return;
        }
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String configUsername = config.getLoginConfig().getUsername();
        String configPassword = config.getLoginConfig().getPassword();

        if (configUsername.equals(username) && configPassword.equals(password)) {
            // Login success
            HttpSession session = req.getSession();
            session.setAttribute("user", username);
            // Redirect to a secure page
            resp.sendRedirect("hikeServlet");
        } else {
            // Login failure
            req.setAttribute("errorMessage", "Invalid username or password");
            req.getRequestDispatcher("Login.jsp").forward(req, resp);
        }
    }


}
