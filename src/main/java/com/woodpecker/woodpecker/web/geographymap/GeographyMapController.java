package com.woodpecker.woodpecker.web.geographymap;

import com.woodpecker.woodpecker.model.GeographyMap;
import com.woodpecker.woodpecker.repository.CrudGeographyMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@Controller
//@RequestMapping("/maps")
public class GeographyMapController {

    @Autowired
    private CrudGeographyMapRepository repository;


    @GetMapping("/update/{id}")
    public String get(Model model, @PathVariable int id) {
        GeographyMap geographyMap = repository.findById(id).get();
        model.addAttribute("map", geographyMap);
        return "mapsForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        repository.delete(id);
        return "redirect:/maps";
    }

}
