package edu.bbte.idde.szim2182.backend.dao;

import edu.bbte.idde.szim2182.backend.datasource.DataSourceUtil;
import edu.bbte.idde.szim2182.backend.models.Hike;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcHikeDao implements HikeDao {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcHikeDao.class);

    private Connection getConnection() throws SQLException {
        return DataSourceUtil.getDataSource().getConnection();
    }

    @Override
    public List<Hike> findAll() {
        List<Hike> hikes = new ArrayList<>();
        String sql = "SELECT * FROM hikes";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                Hike hike = new Hike();
                hike.setId(result.getLong("id"));
                hike.setName(result.getString("name"));
                hike.setDescription(result.getString("description"));
                hike.setDifficultyLevel(result.getInt("difficultyLevel"));
                hike.setLocationId(result.getLong("locationId"));
                hike.setDistance(result.getDouble("distance"));
                hikes.add(hike);
            }
            LOG.info("Retrieved all hikes successfully");
        } catch (SQLException e) {
            LOG.error("Error retrieving all hikes: {}", e.getMessage(), e);
        }
        return hikes;
    }

    @Override
    public Hike findById(Long id) {
        String sql = "SELECT * FROM hikes WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    Hike hike = new Hike();
                    hike.setId(result.getLong("id"));
                    hike.setName(result.getString("name"));
                    hike.setDescription(result.getString("description"));
                    hike.setDifficultyLevel(result.getInt("difficultyLevel"));
                    hike.setLocationId(result.getLong("locationId"));
                    hike.setDistance(result.getDouble("distance"));
                    LOG.info("Found the hike with ID: {}", id);
                    return hike;
                }
            }
        } catch (SQLException e) {
            LOG.error("Error finding hike with ID {}: {}", id, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Hike create(Hike hike) {
        String locationSql = "INSERT INTO locations (startPoint, endPoint) VALUES (?, ?)";
        long locationId = -1;
        try (Connection conn = getConnection();
             PreparedStatement locationStmt = conn.prepareStatement(locationSql, Statement.RETURN_GENERATED_KEYS)) {

            locationStmt.setString(1, hike.getLocation().getStartPoint());
            locationStmt.setString(2, hike.getLocation().getEndPoint());
            int locationAffectedRows = locationStmt.executeUpdate();
            if (locationAffectedRows > 0) {
                try (ResultSet generatedKeys = locationStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        locationId = generatedKeys.getLong(1);
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Error inserting location: {}", e.getMessage(), e);
            return null;
        }

        String hikeSql = "INSERT INTO hikes (name, description, difficultyLevel, locationId, distance)"
                + " VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement hikeStmt = conn.prepareStatement(hikeSql, Statement.RETURN_GENERATED_KEYS)) {

            hikeStmt.setString(1, hike.getName());
            hikeStmt.setString(2, hike.getDescription());
            hikeStmt.setInt(3, hike.getDifficultyLevel());
            hikeStmt.setLong(4, locationId);
            hikeStmt.setDouble(5, hike.getDistance());

            int hikeAffectedRows = hikeStmt.executeUpdate();
            if (hikeAffectedRows > 0) {
                try (ResultSet generatedKeys = hikeStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        hike.setId(generatedKeys.getLong(1));
                        return hike;
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Error creating hike: {}", e.getMessage(), e);
        }
        return null;
    }


    @Override
    public Hike update(Long id, Hike hike) {
        String sql = "UPDATE hikes SET name = ?, description = ?, difficultyLevel = ?, locationId = ?,"
                + " distance = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hike.getName());
            stmt.setString(2, hike.getDescription());
            stmt.setInt(3, hike.getDifficultyLevel());
            stmt.setLong(4, hike.getLocationId());
            stmt.setDouble(5, hike.getDistance());
            stmt.setLong(6, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                LOG.info("Hike updated successfully: {}", hike);
                return hike;
            }
        } catch (SQLException e) {
            LOG.error("Error updating hike with ID {}: {}", id, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Hike> findByName(String name) {
        List<Hike> hikes = new ArrayList<>();
        String sql = "SELECT * FROM hikes WHERE name LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    Hike hike = new Hike();
                    hike.setId(result.getLong("id"));
                    hike.setName(result.getString("name"));
                    hike.setDescription(result.getString("description"));
                    hike.setDifficultyLevel(result.getInt("difficultyLevel"));
                    hike.setLocationId(result.getLong("locationId"));
                    hike.setDistance(result.getDouble("distance"));
                    hikes.add(hike);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error finding hikes with name like {}: {}", name, e.getMessage(), e);
        }
        LOG.info("Found hike(s) with name: {}", name);
        return hikes;

    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM hikes WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                LOG.info("Hike deleted successfully with ID {}", id);
            }
        } catch (SQLException e) {
            LOG.error("Error deleting hike with ID {}: {}", id, e.getMessage(), e);
        }
    }
}

