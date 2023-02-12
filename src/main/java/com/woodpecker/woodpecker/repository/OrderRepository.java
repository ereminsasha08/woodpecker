package com.woodpecker.woodpecker.repository;

import com.woodpecker.woodpecker.model.map.OrderMap;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;


public interface OrderRepository extends BaseRepository<OrderMap> {
    @EntityGraph(attributePaths = {"geographyMap"})
    List<OrderMap> findAll();
    @EntityGraph(attributePaths = {"geographyMap"})
    List<OrderMap> findByIsAvailability(boolean isAvailability);
}
