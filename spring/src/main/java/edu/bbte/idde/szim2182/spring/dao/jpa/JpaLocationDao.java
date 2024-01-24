package edu.bbte.idde.szim2182.spring.dao.jpa;

import edu.bbte.idde.szim2182.spring.dao.LocationDao;
import edu.bbte.idde.szim2182.spring.model.Location;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Profile("jpa")
@Repository
public interface JpaLocationDao extends JpaRepository<Location, Long>, LocationDao {
    @Override
    @Modifying
    @Transactional
    @Query("UPDATE Location SET startPoint = :#{#location.startPoint}, endPoint = :#{#location.endPoint}"
            + " WHERE id = :#{#id}")
    void update(@Param("id") Long id, @Param("location") Location location);
}
