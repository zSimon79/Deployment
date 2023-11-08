package edu.bbte.idde.szim2182;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.szim2182.backend.crud.CrudRepository;
import edu.bbte.idde.szim2182.backend.crud.MemoryCrudRepository;
import edu.bbte.idde.szim2182.backend.models.Hike;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@WebServlet("/hikeServlet")
public class HikeServlet extends HttpServlet {

    private final CrudRepository repository =  MemoryCrudRepository.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(HikeServlet.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AtomicLong idGenerator;
    private final List<Hike> hikes = new CopyOnWriteArrayList<>();

    public HikeServlet() {
        super();
        idGenerator = new AtomicLong();
    }

    @Override
    public void init() {
        LOG.info("Initializing hikeservlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("GET /hikes");

        resp.setHeader("Content-Type", "application/json");

        // If an ID is provided, return the specific hike, otherwise return all hikes
        String idParam = req.getParameter("id");
        if (idParam != null) {
            long id = Long.parseLong(idParam);
            Hike hike = repository.read(id);
            if (hike == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                objectMapper.writeValue(resp.getOutputStream(), hike);
            }
        } else {
            List<Hike> hikes = repository.readAll();
            objectMapper.writeValue(resp.getOutputStream(), hikes);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("POST /hikes");

        try {
            Hike hike = objectMapper.readValue(req.getInputStream(), Hike.class);
            // Set a new ID for the hike
            hike.setId(idGenerator.incrementAndGet());
            hikes.add(hike);
            LOG.info("Received hike: {}", hike);
            // Respond with the created hike
            resp.setHeader("Content-Type", "application/json");
            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getOutputStream(), hike);
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.error("Error creating hike: {}", e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                long id = Long.parseLong(idParam);
                Hike hikeToUpdate = objectMapper.readValue(req.getReader(), Hike.class);
                Hike updatedHike = repository.update(id, hikeToUpdate);
                if (updatedHike != null) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(updatedHike));
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Hike with ID " + id + " not found");
                }
            } catch (IOException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hike ID is required for update");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            long id = Long.parseLong(idParam);
            repository.delete(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Hike ID is required for deletion");
        }
    }
}