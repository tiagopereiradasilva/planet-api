package com.tiago.planetapi.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiago.planetapi.domain.Planet;
import com.tiago.planetapi.service.PlanetService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetService planetService;
    

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }


    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planetService.create(planet));
    }

    @GetMapping("{id}")
    public ResponseEntity<Planet> getMethodName(@PathVariable Long id) {
        return ResponseEntity.ok(planetService.find(id));
    }
    
    
    
}
