package com.woodpecker.woodpecker.web.map;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.service.map.GeographyMapService;
import com.woodpecker.woodpecker.web.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = GeographyMapRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class GeographyMapRestController {
    static final String REST_URL = "/rest/maps";

    private final GeographyMapService mapService;

    @GetMapping
    public List<GeographyMap> userOrderMaps(@AuthenticationPrincipal AuthUser authUser) {
        return mapService.findByManager(authUser.getUser());
    }



    @GetMapping("/{id}")
    public GeographyMap get(@PathVariable int id) {
        return mapService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@Valid GeographyMap geographyMap, @AuthenticationPrincipal AuthUser authUser) {
        mapService.create(geographyMap, authUser);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        mapService.delete(id);
    }

    @GetMapping("/filter")
    public List<GeographyMap> getFiltered(@AuthenticationPrincipal AuthUser authUser,
                                          @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                          @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                          @RequestParam String nameManager) {
        return mapService.getByDateTimeBetween(authUser, startDate, endDate, nameManager);
    }

}
