package edu.bbte.idde.szim2182.spring.dao.jdbc;

import edu.bbte.idde.szim2182.spring.dao.DaoException;
import edu.bbte.idde.szim2182.spring.dao.HikeDao;
import edu.bbte.idde.szim2182.spring.model.Hike;
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
public class JdbcHikeDao implements HikeDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcLocationDao jdbcLocationDao;

    private Hike mapRowToHike(ResultSet resultSet) throws SQLException {
        Hike hike = new Hike();
        Long locationId = resultSet.getLong("locationId");
        hike.setId(resultSet.getLong("id"));
        hike.setName(resultSet.getString("name"));
        hike.setDescription(resultSet.getString("description"));
        hike.setDifficultyLevel(resultSet.getInt("difficultyLevel"));
        hike.setDistance(resultSet.getDouble("distance"));

        Optional<Location> location = jdbcLocationDao.findById(locationId);
        location.ifPresent(hike::setLocation);
        return hike;
    }

    @Override
    public List<Hike> findAll() {
        List<Hike> hikes = new ArrayList<>();
        String sql = "SELECT * FROM hikes";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet result = stmt.executeQuery()) {

            while (result.next()) {
                Hike hike = mapRowToHike(result);
                hikes.add(hike);
            }
            log.info("Retrieved all hikes successfully");
        } catch (SQLException e) {
            log.error("Error retrieving all hikes: {}", e.getMessage(), e);
            throw new DaoException("Error retrieving all hikes: {}", e);
        }
        return hikes;
    }

    @Override
    public Optional<Hike> findById(Long id) {
        String sql = "SELECT * FROM hikes WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    Hike hike = mapRowToHike(result);
                    log.info("Found the hike with ID: {}", id);
                    return Optional.of(hike);
                }
            }
        } catch (SQLException e) {
            log.error("Error finding hike with ID {}: {}", id, e.getMessage(), e);
            throw new DaoException("Error finding hike", e);
        }
        return Optional.empty();
    }

    @Override
    public Hike save(Hike hike) {


        String hikeSql = "INSERT INTO hikes (name, description, difficultyLevel, locationId, distance)"
                + " VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement hikeStmt = conn.prepareStatement(hikeSql, Statement.RETURN_GENERATED_KEYS)) {

            hikeStmt.setString(1, hike.getName());
            hikeStmt.setString(2, hike.getDescription());
            hikeStmt.setInt(3, hike.getDifficultyLevel());
            hikeStmt.setLong(4, hike.getLocation().getId());
            hikeStmt.setDouble(5, hike.getDistance());
            Optional<Location> location = jdbcLocationDao.findById(hike.getLocation().getId());

            int hikeAffectedRows = hikeStmt.executeUpdate();
            if (hikeAffectedRows > 0) {
                try (ResultSet generatedKeys = hikeStmt.getGeneratedKeys()) {
                    if (generatedKeys.next() && location.isPresent()) {
                        hike.setId(generatedKeys.getLong(1));
                        hike.setLocation(location.get());
                        return hike;
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Error creating hike: {}", e.getMessage(), e);
            throw new DaoException("Error creating: {}", e);
        }
        return null;
    }


    @Override
    public void update(Long id, Hike hike) {
        String sql = "UPDATE hikes SET name = ?, description = ?, difficultyLevel = ?, locationId = ?,"
                + " distance = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hike.getName());
            stmt.setString(2, hike.getDescription());
            stmt.setInt(3, hike.getDifficultyLevel());
            stmt.setLong(4, hike.getLocation().getId());
            stmt.setDouble(5, hike.getDistance());
            stmt.setLong(6, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                log.info("Hike updated successfully: {}", hike);
            }
        } catch (SQLException e) {
            log.error("Error updating hike with ID {}: {}", id, e.getMessage(), e);
            throw new DaoException("Error updating hike: {}", e);
        }
    }

    @Override
    public Hike saveAndFlush(Hike hike) {
        if (hike.getId() == null || hike.getId() == 0) {
            return save(hike);
        } else {
            update(hike.getId(), hike);
            return hike;
        }
    }

    @Override
    public List<Hike> findByName(String name) {
        List<Hike> hikes = new ArrayList<>();
        String sql = "SELECT * FROM hikes WHERE name LIKE ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + name + "%");
            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    Hike hike = mapRowToHike(result);
                    hikes.add(hike);
                }
            }
        } catch (SQLException e) {
            log.error("Error finding hikes with name like {}: {}", name, e.getMessage(), e);
            throw new DaoException("Error finding hike: {}", e);
        }
        log.info("Found hike(s) with name: {}", name);
        return hikes;

    }

    @Override
    public List<Hike> findByLocationId(Long locationId) {
        List<Hike> hikes = new ArrayList<>();
        String sql = "SELECT * FROM hikes WHERE locationId = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, locationId);
            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    Hike hike = mapRowToHike(result);
                    hikes.add(hike);
                }
            }
            log.info("Found hikes for location ID: {}", locationId);
        } catch (SQLException e) {
            log.error("Error finding hikes for location ID {}: {}", locationId, e.getMessage(), e);
            throw new DaoException("Error finding hikes for location ID: {}", e);
        }
        return hikes;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM hikes WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                log.info("Hike deleted successfully with ID {}", id);
            }
        } catch (SQLException e) {
            log.error("Error deleting hike with ID {}: {}", id, e.getMessage(), e);
            throw new DaoException("Error deleting hike: {}", e);
        }
    }

    //    @Override
    //    public void deleteAll(List<Hike> hikes) {
    //        hikes.forEach(hike -> delete(hike.getId()));
    //    }
}

