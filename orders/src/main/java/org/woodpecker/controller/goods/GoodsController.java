package org.woodpecker.controller.goods;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woodpecker.repository.model.goods.Good;
import org.woodpecker.repository.model.goods.WorldMap;
import org.woodpecker.service.goods.GoodsService;

import java.util.List;

@RestController
@RequestMapping(GoodsController.URL)
@RequiredArgsConstructor
@CrossOrigin("*")
public class GoodsController {

    private final GoodsService goodsService;
    public static final String URL = "v1/rest/goods";

    @GetMapping
    public ResponseEntity<List<Good>> getAll() {
        return ResponseEntity.ok(goodsService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Good> getById(@PathVariable Integer id){
        return ResponseEntity.ok(goodsService.getById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Good> save(@RequestBody Good good) {
        return ResponseEntity.status(HttpStatus.CREATED).body(goodsService.create(good));
    }

    @PutMapping("/save/{id}")
    public ResponseEntity<Good> save(@RequestBody Good good, @PathVariable Integer id) {
        return ResponseEntity.ok(goodsService.update(good, id));
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        goodsService.deleteById(id);
    }

    @PostMapping("/map/world/save")
    public ResponseEntity<Good> saveWorldMap(@RequestBody WorldMap worldMap){
        return ResponseEntity.status(HttpStatus.CREATED).body(goodsService.create(worldMap));
    }

}
