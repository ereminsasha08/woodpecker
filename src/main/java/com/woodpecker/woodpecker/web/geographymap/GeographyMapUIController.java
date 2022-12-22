package com.woodpecker.woodpecker.web.geographymap;


import com.woodpecker.woodpecker.model.GeographyMap;
import com.woodpecker.woodpecker.repository.CrudGeographyMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/maps", produces = MediaType.APPLICATION_JSON_VALUE)
public class GeographyMapUIController {

    @Autowired
    private CrudGeographyMapRepository repository;

    @GetMapping
    public List<GeographyMap> getAll() {
        return repository.findAll();
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

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@RequestParam String typeMap,
                       @RequestParam int conditionMap,
                       @RequestParam int size,
                       @RequestParam String language,
                       @RequestParam String multiLevel,
                       @RequestParam String color,
                       @RequestParam String description,
                       @RequestParam String state,
                       @RequestParam String dateTime) {


        GeographyMap geographyMap = new GeographyMap(typeMap,
                conditionMap,
                size,
                language,
                Boolean.parseBoolean(multiLevel),
                Boolean.parseBoolean(state),
                color,
                description,
                LocalDateTime.parse(dateTime));
        geographyMap.setManager("ld");
        geographyMap.setLight("ld");
        geographyMap.setAdditional("");
        geographyMap.setContact("");
        geographyMap.setPrice(768);
        repository.save(geographyMap);

    }

}
