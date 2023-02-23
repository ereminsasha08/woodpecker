package com.woodpecker.woodpecker.service.map;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.OrderMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.model.user.User;
import com.woodpecker.woodpecker.repository.GeographyMapRepository;
import com.woodpecker.woodpecker.service.order.CutService;
import com.woodpecker.woodpecker.util.exception.ApplicationException;
import com.woodpecker.woodpecker.util.exception.ErrorType;
import com.woodpecker.woodpecker.web.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

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
        return geographyMapRepository.findByManagerAndIsView(user, true).stream()
                .filter(
                        map -> map.getOrderMap() == null
                                || !(map.getOrderMap().getIsAvailability()
                                || map.getOrderMap().getCompleted())
                )
                .toList();
    }

    public List<GeographyMap> getByDateTimeBetween(AuthUser authUser, LocalDateTime startDate, LocalDateTime endDate, String nameManager, boolean isPost) {
        startDate = startDate != null ? startDate : LocalDateTime.of(2000, 1, 1, 0, 0);
        endDate = endDate != null ? startDate : LocalDateTime.of(2040, 1, 1, 0, 0);
        Stream<GeographyMap> byDateTimeBetween = geographyMapRepository.getByDateTimeBetweenAndIsView(startDate, endDate, true).stream();
        if (!nameManager.isBlank() && "мои".equalsIgnoreCase(nameManager)) {
            byDateTimeBetween = byDateTimeBetween
                    .filter(
                            map -> map.getManager().id() == authUser.id()
                    );
        } else if (!nameManager.isBlank() && !"все".equalsIgnoreCase(nameManager)) {
            byDateTimeBetween = byDateTimeBetween
                    .filter(
                            map -> map.getManager().getName().equalsIgnoreCase(nameManager)
                    );
        }
        if (!isPost) {
            byDateTimeBetween = byDateTimeBetween
                    .filter(
                            map -> map.getOrderMap() == null
                                    || !(map.getOrderMap().getIsAvailability()
                                    || map.getOrderMap().getCompleted())
                    );
        } else {
            byDateTimeBetween = byDateTimeBetween
                    .filter(
                            map -> map.getOrderMap() != null
                                    && map.getOrderMap().getCompleted()
                                    && !map.getOrderMap().getIsAvailability());
        }
        return byDateTimeBetween.toList();
    }


    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void create(GeographyMap geographyMap, AuthUser authUser) {
        if (geographyMap.isNew()) {
            geographyMap.setManager(authUser.getUser());
        } else {
            assert geographyMap.getId() != null;
            GeographyMap byId = getById(geographyMap.id());
//            if (byId.getManager().id() != authUser.getUser().id())
//                throw new IllegalArgumentException("Изменять можно только свои заказы");
//            else {
            geographyMap.setManager(byId.getManager());
            geographyMap.setIsColorPlywood(byId.getIsColorPlywood());
            geographyMap.setOrderMap(byId.getOrderMap());
//            }
        }
        geographyMapRepository.save(geographyMap);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "mapsByManager", allEntries = true)})
    public void delete(int id) {
        GeographyMap byId = getById(id);

        byId.setView(false);
        OrderMap orderMap = byId.getOrderMap();
        if (orderMap != null) {
            orderMap.setCompleted(true);
            orderMap.setIsAvailability(false);
            if (orderMap.getStage() == Stage.ПИЛИТСЯ.ordinal()) {


                cutService.refreshCapacity(orderMap);
            }
        }

    }
}
