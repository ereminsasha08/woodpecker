package com.woodpecker.woodpecker.service.map;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.model.user.User;
import com.woodpecker.woodpecker.repository.GeographyMapRepository;
import com.woodpecker.woodpecker.repository.OrderRepository;
import com.woodpecker.woodpecker.service.order.OrderService;
import com.woodpecker.woodpecker.util.exception.ApplicationException;
import com.woodpecker.woodpecker.util.exception.ErrorType;
import com.woodpecker.woodpecker.web.AuthUser;
import lombok.RequiredArgsConstructor;
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
    private final OrderService orderService;


    public GeographyMap getById(Integer id) {
        return geographyMapRepository.findById(id).orElseThrow(() -> new ApplicationException("Карты не существует", ErrorType.DATA_NOT_FOUND));
    }


    public List<GeographyMap> findByManager(User user) {
        return geographyMapRepository.findByManagerAndIsView(user, true).stream()
                .filter(
                        map -> map.getOrderMap() == null
                                || map.getOrderMap().getStage() < Stage.ОТПРАВЛЕН.ordinal()
                                || map.getOrderMap().getStage() == Stage.ЗАКАЗ_ИЗ_НАЛИЧИЯ.ordinal()
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
                                    || map.getOrderMap().getStage() < Stage.ОТПРАВЛЕН.ordinal()
                                    || map.getOrderMap().getStage() == Stage.ЗАКАЗ_ИЗ_НАЛИЧИЯ.ordinal()
                    );
        }
        return byDateTimeBetween.toList();
    }


    @Transactional
    public void create(GeographyMap geographyMap, AuthUser authUser) {
        if (geographyMap.isNew()) {
            geographyMap.setManager(authUser.getUser());
        } else {
            assert geographyMap.getId() != null;
            geographyMap = geographyMapRepository.findById(geographyMap.getId())
                    .orElseThrow(() -> new ApplicationException("Карта не может быть обновленна, так как не найдена", ErrorType.DATA_ERROR));
            if (geographyMap.getManager().id() != authUser.getUser().id())
                throw new IllegalArgumentException("Изменять можно только свои заказы");
        }
        geographyMapRepository.save(geographyMap);
    }

    @Transactional
    public void delete(int id) {
        GeographyMap byId = getById(id);
        byId.setView(false);
        try {
            byId.getOrderMap().setCompleted(true);
            byId.getOrderMap().setIsAvailability(false);
        } catch (NullPointerException e) {

        }
    }


}
