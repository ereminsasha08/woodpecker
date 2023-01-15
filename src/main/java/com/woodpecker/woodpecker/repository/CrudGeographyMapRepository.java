package com.woodpecker.woodpecker.repository;

import com.woodpecker.woodpecker.model.GeographyMap;
import com.woodpecker.woodpecker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudGeographyMapRepository extends JpaRepository<GeographyMap, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM GeographyMap gm WHERE gm.id=:id")
    int delete(@Param("id") int id);

    List<GeographyMap> findByIdAfterOrderByIdDesc(int id);

    List<GeographyMap> findByManager(User user);


    List<GeographyMap> getByDateTimeBetweenAndConditionMapAfterOrderById(LocalDateTime startDate, LocalDateTime endDate, int conditionMap);
}
