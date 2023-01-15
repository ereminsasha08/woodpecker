package com.woodpecker.woodpecker.web.geographymap;


import com.woodpecker.woodpecker.model.GeographyMap;
import com.woodpecker.woodpecker.repository.CrudGeographyMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping(value = "/maps", produces = MediaType.APPLICATION_JSON_VALUE)
public class GeographyMapUIController {

    @Autowired
    private CrudGeographyMapRepository repository;

    @GetMapping
    public String getAll() {
        return "maps";
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
    public void create(@Valid GeographyMap geographyMap) {
        repository.save(geographyMap);

    }

}
