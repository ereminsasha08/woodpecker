package org.woodpecker.repository;

import org.woodpecker.repository.model.goods.WorldMap;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface GeographyMapRepository extends BaseRepository<WorldMap> {
    @EntityGraph(attributePaths = {"orderMap"})
    List<WorldMap> findByIsView(boolean isView);

    @EntityGraph(attributePaths = {"orderMap"})
    Optional<WorldMap> findById(Integer id);
}
