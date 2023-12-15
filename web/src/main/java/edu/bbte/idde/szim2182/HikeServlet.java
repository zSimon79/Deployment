package edu.bbte.idde.szim2182;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.bbte.idde.szim2182.backend.dao.DaoFactory;
import edu.bbte.idde.szim2182.backend.dao.HikeDao;
import edu.bbte.idde.szim2182.backend.dao.DaoException;
import edu.bbte.idde.szim2182.backend.models.Hike;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet("/hikeServlet")
public class HikeServlet extends HttpServlet {

    private HikeDao hikeDao;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        super.init();
        log.info("Initializing HikeServlet");

        DaoFactory daoFactory = DaoFactory.getInstance();
        this.hikeDao = daoFactory.getHikeDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.info("GET /hikes");

        resp.setHeader("Content-Type", "application/json");

        // If an ID is provided, return the specific hike, otherwise return all hikes
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                long id = Long.parseLong(idParam);
                Hike hike = hikeDao.findById(id);
                if (hike == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                } else {
                    objectMapper.writeValue(resp.getOutputStream(), hike);
                }
            } catch (NumberFormatException e) {
                sendJsonError(resp, "Invalid hike ID format ");
            } catch (DaoException e) {
                sendJsonError(resp, "Error getting hike: " + e.getMessage());
                log.error("Error getting hike: {}", e.getMessage(), e);
            }
        } else {
            List<Hike> hikes = hikeDao.findAll();
            req.setAttribute("hikes", hikes);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/hikes.jsp");
            dispatcher.forward(req, resp);
            objectMapper.writeValue(resp.getOutputStream(), hikes);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.info("POST /hikes");

        try {
            Hike hike = objectMapper.readValue(req.getInputStream(), Hike.class);
            Hike createdHike = hikeDao.create(hike);
            log.info("Received hike: {}", createdHike);
            resp.setHeader("Content-Type", "application/json");
            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getOutputStream(), createdHike);
        } catch (IOException e) {
            sendJsonError(resp, e.getMessage());
            log.error("Error creating hike: {}", e.getMessage());
        } catch (DaoException e) {
            sendJsonError(resp, "Error creating hike: " + e.getMessage());
            log.error("Error creating hike: {}", e.getMessage(), e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                long id = Long.parseLong(idParam);
                Hike hikeToUpdate = objectMapper.readValue(req.getReader(), Hike.class);
                Hike updatedHike = hikeDao.update(id, hikeToUpdate);
                if (updatedHike != null) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(updatedHike));
                } else {
                    sendJsonError(resp, "Hike with ID " + id + " not found");

                }
            } catch (NumberFormatException e) {
                sendJsonError(resp, "Invalid hike ID format");

            } catch (IOException e) {
                sendJsonError(resp, e.getMessage());
            } catch (DaoException e) {
                sendJsonError(resp, "Error updating hike: " + e.getMessage());
                log.error("Error updating hike: {}", e.getMessage(), e);
            }
        } else {
            sendJsonError(resp, "Hike ID is required for update");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                long id = Long.parseLong(idParam);
                hikeDao.delete(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                sendJsonError(resp, "Invalid hike ID format");
            } catch (DaoException e) {
                sendJsonError(resp, "Error deleting hike: " + e.getMessage());
                log.error("Error deleting hike: {}", e.getMessage(), e);
            }
        } else {
            sendJsonError(resp, "Hike ID is required for deletion");
        }
    }

    private void sendJsonError(HttpServletResponse resp, String errorMessage) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("application/json");
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("error", errorMessage);
        objectMapper.writeValue(resp.getOutputStream(), errorNode);
    }
}