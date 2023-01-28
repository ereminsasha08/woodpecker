package com.woodpecker.woodpecker.service;

import com.woodpecker.woodpecker.model.GeographyMap;
import com.woodpecker.woodpecker.repository.GeographyMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeographyMapService {

    private final GeographyMapRepository  geographyMapRepository;


    public GeographyMap getById(Integer id) {
        return geographyMapRepository.findById(id).orElseThrow();
    }
}
