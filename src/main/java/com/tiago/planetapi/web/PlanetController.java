package com.tiago.planetapi.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiago.planetapi.domain.Planet;
import com.tiago.planetapi.service.PlanetService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetService planetService;
    

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping()
    public ResponseEntity<List<Planet>> getMethodName(@RequestParam(required = false) String climate, @RequestParam(required = false) String terrain) {
        return ResponseEntity.ok(planetService.list(climate, terrain));
    }
    

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planetService.create(planet));
    }

    @GetMapping("{id}")
    public ResponseEntity<Planet> getById(@PathVariable Long id) {
        return ResponseEntity.ok(planetService.find(id));
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<Planet> getByName(@PathVariable String name) {
        return ResponseEntity.ok(planetService.findByName(name));
    }
    
}
