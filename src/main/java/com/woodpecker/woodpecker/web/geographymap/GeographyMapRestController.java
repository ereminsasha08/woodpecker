package com.woodpecker.woodpecker.web.geographymap;

import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.repository.GeographyMapRepository;
import com.woodpecker.woodpecker.service.GeographyMapService;
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

    private final GeographyMapRepository repository;

    private final GeographyMapService mapService;


    @GetMapping
    public List<GeographyMap> userOrderMaps(@AuthenticationPrincipal AuthUser authUser) {
        return repository.findByManager(authUser.getUser());
    }

    @GetMapping("/{id}")
    public GeographyMap get(@PathVariable int id) {
        return repository.findById(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@Valid GeographyMap geographyMap, @AuthenticationPrincipal AuthUser authUser) {
        mapService.create(geographyMap, authUser);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        repository.delete(id);
    }

    @GetMapping("/filter")
    public List<GeographyMap> getFiltered(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                          @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                          @RequestParam int conditionMapFilter) {
        startDate = startDate != null ? startDate : LocalDateTime.of(2000, 1, 1, 0, 0);
        endDate = endDate != null ? startDate : LocalDateTime.of(2040, 1, 1, 0, 0);
        return repository.getByDateTimeBetweenAndConditionMapAfterOrderById(startDate, endDate, conditionMapFilter - 1);
    }

}
