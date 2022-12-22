package com.woodpecker.woodpecker.web.geographymap;

import com.woodpecker.woodpecker.model.GeographyMap;
import com.woodpecker.woodpecker.repository.CrudGeographyMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = GeographyMapRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class GeographyMapRestController {

    static final String REST_URL = "/rest/maps";

    @Autowired
    private CrudGeographyMapRepository repository;

    @GetMapping
    public List<GeographyMap> allMap() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public GeographyMap get(@PathVariable int id) {
        return repository.findById(id).get();
    }

}
