package com.woodpecker.woodpecker.web.geographymap;

import com.woodpecker.woodpecker.model.GeographyMap;
import com.woodpecker.woodpecker.repository.CrudGeographyMapRepository;
import com.woodpecker.woodpecker.web.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GeographyMapRestController {

    static final String REST_URL = "/rest/maps";

    @Autowired
    private CrudGeographyMapRepository repository;

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
        geographyMap.setManager(authUser.getUser());
        repository.save(geographyMap);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        repository.delete(id);
    }

    @GetMapping("/filter")
    public List<GeographyMap> getFiltered(@RequestParam @Nullable LocalDateTime startDate,
                                          @RequestParam @Nullable LocalDateTime endDate,
                                          @RequestParam @Nullable int conditionMapFilter) {
        return repository.getByDateTimeBetweenAndConditionMapAfterOrderById(startDate, endDate, conditionMapFilter - 1);
    }

}
