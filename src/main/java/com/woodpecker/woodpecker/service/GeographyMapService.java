package com.woodpecker.woodpecker.service;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.repository.GeographyMapRepository;
import com.woodpecker.woodpecker.web.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GeographyMapService {

    private final GeographyMapRepository geographyMapRepository;


    public GeographyMap getById(Integer id) {
        return geographyMapRepository.findById(id).orElseThrow();
    }

    public void create(GeographyMap geographyMap, AuthUser authUser) {
        if (geographyMap.isNew()) {
            geographyMap.setManager(authUser.getUser());
        } else {
            GeographyMap byId = geographyMapRepository.findById(geographyMap.getId()).get();
            if (byId.getManager().id() == authUser.getUser().id()) {
                geographyMap.setManager(authUser.getUser());
            } else throw new IllegalArgumentException("Изменять можно только свои заказы");
        }
        geographyMapRepository.save(geographyMap);

    }

}
