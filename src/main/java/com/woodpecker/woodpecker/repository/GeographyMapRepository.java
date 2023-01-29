package com.woodpecker.woodpecker.repository;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GeographyMapRepository extends BaseRepository<GeographyMap> {

    @Modifying
    @Query("DELETE FROM GeographyMap gm WHERE gm.id=:id")
    int delete(@Param("id") int id);

    List<GeographyMap> findByManager(User user);


    List<GeographyMap> getByDateTimeBetweenAndConditionMapAfterOrderById(LocalDateTime startDate, LocalDateTime endDate, int conditionMap);
}
