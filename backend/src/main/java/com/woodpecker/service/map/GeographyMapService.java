package com.woodpecker.service.map;

import com.woodpecker.model.user.User;
import com.woodpecker.repository.GeographyMapRepository;
import com.woodpecker.service.order.CutService;
import com.woodpecker.util.exception.ApplicationException;
import com.woodpecker.util.exception.ErrorType;
import com.woodpecker.model.map.GeographyMap;
import com.woodpecker.controller.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeographyMapService {

    private final GeographyMapRepository geographyMapRepository;

    private final CutService cutService;


    public GeographyMap getById(Integer id) {
        return geographyMapRepository.findById(id).orElseThrow(() -> new ApplicationException("Карты не существует", ErrorType.DATA_NOT_FOUND));
    }

    @Cacheable("mapsByManager")
    public List<GeographyMap> findByManager(User user) {
        return geographyMapRepository.findByIsView(true);
//                .stream()
//                .filter(
//                        map -> map.getOrder() == null
//                                || !(map.getOrder().getIsAvailability()
//                                || map.getOrder().getCompleted())
//                )
//                .toList();
    }

    public List<GeographyMap> getByDateTimeBetween(AuthUser authUser, LocalDateTime startDate, LocalDateTime endDate, String nameManager, boolean isPost) {
        startDate = startDate != null ? startDate : LocalDateTime.of(2000, 1, 1, 0, 0);
        endDate = endDate != null ? startDate : LocalDateTime.of(2040, 1, 1, 0, 0);
//        Stream<GeographyMap> byDateTimeBetween = geographyMapRepository.getByDateTimeBetweenAndIsView(startDate, endDate, true).stream();
//        if (!nameManager.isBlank() && "мои".equalsIgnoreCase(nameManager)) {
//            byDateTimeBetween = byDateTimeBetween
//                    .filter(
//                            map -> map.getManager().id() == authUser.id()
//                    );
//        } else if (!nameManager.isBlank() && !"все".equalsIgnoreCase(nameManager)) {
//            byDateTimeBetween = byDateTimeBetween
//                    .filter(
//                            map -> map.getManager().getName().equalsIgnoreCase(nameManager)
//                    );
//        }
//        if (!isPost) {
//            byDateTimeBetween = byDateTimeBetween
//                    .filter(
//                            map -> map.getOrder() == null
//                                    || !(map.getOrder().getIsAvailability()
//                                    || map.getOrder().getCompleted())
//                    );
//        } else {
//            byDateTimeBetween = byDateTimeBetween
//                    .filter(
//                            map -> map.getOrder() != null
//                                    && map.getOrder().getCompleted()
//                                    && !map.getOrder().getIsAvailability());
//        }
        return Collections.emptyList();
    }


    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void create(GeographyMap geographyMap, AuthUser authUser) {
        if (geographyMap.isNew()) {
//            geographyMap.setManager(authUser.getUser());
        } else {
            assert geographyMap.getId() != null;
            GeographyMap byId = getById(geographyMap.id());
//            if (byId.getManager().id() != authUser.getUser().id())
//                throw new IllegalArgumentException("Изменять можно только свои заказы");
//            else {
//            geographyMap.setManager(byId.getManager());
            geographyMap.setIsColorPlywood(byId.getIsColorPlywood());
//            geographyMap.setOrder(byId.getOrder());
//            }
        }
        geographyMapRepository.save(geographyMap);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void delete(int id) {
//        GeographyMap byId = getById(id);
//
//        byId.setView(false);
//        Order order = byId.getOrder();
//        if (order != null) {
//            order.setCompleted(true);
//            order.setIsAvailability(false);
//            if (Stage.CUTTING.equals(order.getStage())) {
//                cutService.refreshCapacity(order);
//            }
//        }
    }
}
