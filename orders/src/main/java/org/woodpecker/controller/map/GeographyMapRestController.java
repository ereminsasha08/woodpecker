package org.woodpecker.controller.map;

import org.woodpecker.repository.model.goods.WorldMap;
import org.woodpecker.service.map.GeographyMapService;
import org.woodpecker.controller.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = GeographyMapRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin("*")
public class GeographyMapRestController {
    static final String REST_URL = "/rest/maps/";

    private final GeographyMapService mapService;

    @GetMapping
    public List<WorldMap> userOrderMaps(@AuthenticationPrincipal AuthUser authUser) {
        return mapService.findByManager(authUser.getUser());
    }


    @GetMapping("/{id}")
    public WorldMap get(@PathVariable int id) {
        return mapService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@Valid WorldMap worldMap, @AuthenticationPrincipal AuthUser authUser) {
        mapService.create(worldMap, authUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        mapService.delete(id);
    }

    @GetMapping("/filter")
    public List<WorldMap> getFiltered(@AuthenticationPrincipal AuthUser authUser,
                                      @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                      @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                      @RequestParam String nameManager,
                                      @RequestParam boolean isPost) {
        return mapService.getByDateTimeBetween(authUser, startDate, endDate, nameManager, isPost);
    }


}
