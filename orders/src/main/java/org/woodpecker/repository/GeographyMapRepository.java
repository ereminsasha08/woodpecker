package org.woodpecker.repository;

import org.woodpecker.model.map.GeographyMap;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface GeographyMapRepository extends BaseRepository<GeographyMap> {
    @EntityGraph(attributePaths = {"orderMap"})
    List<GeographyMap> findByIsView(boolean isView);

    @EntityGraph(attributePaths = {"orderMap"})
    Optional<GeographyMap> findById(Integer id);
}
