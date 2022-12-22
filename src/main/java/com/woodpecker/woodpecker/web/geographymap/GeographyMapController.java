package com.woodpecker.woodpecker.web.geographymap;

import com.woodpecker.woodpecker.model.GeographyMap;
import com.woodpecker.woodpecker.repository.CrudGeographyMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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

    @GetMapping("/create")
    public String addMap(Model model) {
        model.addAttribute("map", new GeographyMap("Ya", LocalDateTime.now()));
        return "mapsForm";
    }

    @PostMapping("/create")
    public String addMap(HttpServletRequest request) {

        GeographyMap geographyMap = new GeographyMap(request.getParameter("typeMap"),
                Integer.parseInt(request.getParameter("conditionMap")),
                Integer.parseInt(request.getParameter("size")),
                request.getParameter("language"),
                Boolean.parseBoolean(request.getParameter("isMultiLevel")),
                Boolean.parseBoolean(request.getParameter("isState")),
                request.getParameter("color"),
                request.getParameter("description"),
                LocalDateTime.parse(request.getParameter("dateTime")));
        geographyMap.setManager("ld");
        geographyMap.setLight("ld");
        geographyMap.setAdditional("");
        geographyMap.setManager("");
        geographyMap.setContact("");
        geographyMap.setPrice(768);
        repository.save(geographyMap);
        return "redirect:/maps";
    }
}
