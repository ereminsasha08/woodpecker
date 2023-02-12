package com.woodpecker.woodpecker.repository;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GeographyMapRepository extends BaseRepository<GeographyMap> {



    @EntityGraph(attributePaths = {"orderMap"})
    List<GeographyMap> findByManagerAndIsView(User user, boolean isView);

    @EntityGraph(attributePaths = {"orderMap"})
    List<GeographyMap> getByDateTimeBetweenAndIsView(LocalDateTime startDate, LocalDateTime endDate, boolean view);

    @EntityGraph(attributePaths = {"orderMap"})
    Optional<GeographyMap> findById(Integer id);
}
