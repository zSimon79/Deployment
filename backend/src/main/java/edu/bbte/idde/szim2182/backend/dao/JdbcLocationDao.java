package edu.bbte.idde.szim2182.backend.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.bbte.idde.szim2182.backend.models.Location;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

@Slf4j
public class JdbcLocationDao implements LocationDao {

    private final DataSource dataSource;

    public JdbcLocationDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                Location location = new Location();
                location.setId(result.getLong("id"));
                location.setStartPoint(result.getString("startPoint"));
                location.setEndPoint(result.getString("endPoint"));
                locations.add(location);
            }
            log.info("Retrieved all locations successfully");
        } catch (SQLException e) {
            log.error("Error retrieving all locations: {}", e.getMessage(), e);
            throw new DaoException("Error retrieving all locations: {}", e);
        }
        return locations;
    }


    @Override
    public Location findById(Long id) {
        String sql = "SELECT * FROM locations WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    Location location = new Location();
                    location.setId(result.getLong("id"));
                    location.setStartPoint(result.getString("startPoint"));
                    location.setEndPoint(result.getString("endPoint"));
                    log.info("Found the location with ID: {}", id);

                    return location;
                }
            }
        } catch (SQLException e) {
            log.error("Error finding location with ID {}: {}", id, e.getMessage(), e);
            throw new DaoException("Error finding location: {}", e);
        }
        return null;
    }

    @Override
    public Location create(Location location) {
        String sql = "INSERT INTO locations (startPoint, endPoint) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, location.getStartPoint());
            stmt.setString(2, location.getEndPoint());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        location.setId(generatedKeys.getLong(1));
                        log.info("Location created successfully: {}", location);
                        return location;
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Error creating location: {}", e.getMessage(), e);
            throw new DaoException("Error creating location: {}", e);
        }
        return null;
    }

    @Override
    public Location update(Long id, Location location) {
        String sql = "UPDATE locations SET startPoint = ?, endPoint = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, location.getStartPoint());
            stmt.setString(2, location.getEndPoint());
            stmt.setLong(3, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                log.info("Location updated successfully: {}", location);
                return location;
            }
        } catch (SQLException e) {
            log.error("Error updating location with ID {}: {}", id, e.getMessage(), e);
            throw new DaoException("Error updating location: {}", e);

        }
        return null;
    }

    @Override
    public List<Location> findByName(String name) {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations WHERE name LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    Location location = new Location();
                    location.setId(result.getLong("id"));
                    location.setStartPoint(result.getString("startPoint"));
                    location.setEndPoint(result.getString("endPoint"));
                    locations.add(location);
                }
            }
        } catch (SQLException e) {
            log.error("Error finding location with name {}: {}", name, e.getMessage(), e);
            throw new DaoException("Error finding location: {}", e);
        }
        return locations;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM locations WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                log.info("Location deleted successfully with ID {}", id);
            }
        } catch (SQLException e) {
            log.error("Error deleting location with ID {}: {}", id, e.getMessage(), e);
            throw new DaoException("Error deleting location: {}", e);
        }
    }
}
