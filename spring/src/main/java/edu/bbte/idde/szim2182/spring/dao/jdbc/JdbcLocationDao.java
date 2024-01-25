package edu.bbte.idde.szim2182.spring.dao.jdbc;

import edu.bbte.idde.szim2182.spring.dao.DaoException;
import edu.bbte.idde.szim2182.spring.dao.LocationDao;
import edu.bbte.idde.szim2182.spring.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Profile("jdbc")
@Repository
public class JdbcLocationDao implements LocationDao {

    @Autowired
    private DataSource dataSource;


    @Override
    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations";
        try (Connection conn = dataSource.getConnection();
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
    public Optional<Location> findById(Long id) {
        String sql = "SELECT * FROM locations WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    Location location = new Location();
                    location.setId(result.getLong("id"));
                    location.setStartPoint(result.getString("startPoint"));
                    location.setEndPoint(result.getString("endPoint"));
                    log.info("Found the location with ID: {}", id);

                    return Optional.of(location);
                }
            }
        } catch (SQLException e) {
            log.error("Error finding location with ID {}: {}", id, e.getMessage(), e);
            throw new DaoException("Error finding location: {}", e);
        }
        return Optional.empty();
    }

    @Override
    public Location save(Location location) {
        String sql = "INSERT INTO locations (startPoint, endPoint) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
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
    public void update(Long id, Location location) {
        String sql = "UPDATE locations SET startPoint = ?, endPoint = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, location.getStartPoint());
            stmt.setString(2, location.getEndPoint());
            stmt.setLong(3, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                log.info("Location updated successfully: {}", location);
            }
        } catch (SQLException e) {
            log.error("Error updating location with ID {}: {}", id, e.getMessage(), e);
            throw new DaoException("Error updating location: {}", e);

        }
    }

    @Override
    public Location saveAndFlush(Location location) {
        if (location.getId() == null || location.getId() == 0) {
            return save(location);
        } else {
            update(location.getId(), location);
            return location;
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM locations WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
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

    //    @Override
    //    public void deleteAll(List<Hike> hikes) {
    //        log.info("Deleting all locations");
    //        String sql = "DELETE FROM locations";
    //        try (Connection conn = dataSource.getConnection();
    //             PreparedStatement stmt = conn.prepareStatement(sql)) {
    //
    //            int affectedRows = stmt.executeUpdate();
    //            if (affectedRows > 0) {
    //                log.info("All locations deleted successfully");
    //            }
    //        } catch (SQLException e) {
    //            log.error("Error deleting all locations: {}", e.getMessage(), e);
    //            throw new DaoException("Error deleting all locations: {}", e);
    //        }
    //    }
}
