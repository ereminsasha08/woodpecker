package com.woodpecker.woodpecker.repository;

import com.woodpecker.woodpecker.model.map.OrderMap;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends BaseRepository<OrderMap> {
    @EntityGraph(attributePaths = {"geographyMap"})
    List<OrderMap> findByCompleted(boolean isCompleted);

    @EntityGraph(attributePaths = {"geographyMap"})
    List<OrderMap> findByIsAvailability(boolean isAvailability);

}
