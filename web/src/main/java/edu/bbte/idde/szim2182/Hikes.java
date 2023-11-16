package edu.bbte.idde.szim2182;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.szim2182.backend.crud.MemoryCrudRepository;
import edu.bbte.idde.szim2182.backend.models.Hike;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/hikes")
public class Hikes extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(Hikes.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private transient MemoryCrudRepository crudRepository;

    @Override
    public void init() {
        LOG.info("Initializing hikes");
        crudRepository = MemoryCrudRepository.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("GET /hikes");

        // jelezzük a HTTP válaszban, hogy JSON típusú kimenetet küldünk
        resp.setHeader("Content-Type", "application/json");

        List<Hike> hikes = crudRepository.readAll();

        // Log each hike
        for (Hike hike : hikes) {
            LOG.info("Hike: {}", hike);
        }

        // Set the hikes as a request attribute
        req.setAttribute("hikes", hikes);

        // Forward to the JSP page
        RequestDispatcher dispatcher = req.getRequestDispatcher("hikes.jsp");
        dispatcher.forward(req, resp);
    }
}
