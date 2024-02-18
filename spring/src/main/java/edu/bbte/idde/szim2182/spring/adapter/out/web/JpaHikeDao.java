package edu.bbte.idde.szim2182.spring.adapter.out.web;

import edu.bbte.idde.szim2182.spring.application.domain.service.HikeDao;
import edu.bbte.idde.szim2182.spring.application.domain.entity.Hike;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Profile("jpa")
@Repository
public interface JpaHikeDao extends JpaRepository<Hike, Long>, HikeDao {

    @Override
    @Modifying
    @Transactional
    @Query("UPDATE Hike SET name = :#{#hike.name}, description = :#{#hike.description}, "
            + "difficultyLevel = :#{#hike.difficultyLevel}, "
            + "distance =:#{#hike.distance}, location = :#{#hike.location} WHERE id = :#{#id}")
    void update(@Param("id") Long id, @Param("hike") Hike hike);

}
