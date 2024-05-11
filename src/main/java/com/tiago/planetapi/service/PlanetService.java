package com.tiago.planetapi.service;

import org.springframework.stereotype.Service;

import com.tiago.planetapi.domain.Planet;
import com.tiago.planetapi.repository.PlanetRepository;

@Service
public class PlanetService {
    
    private final PlanetRepository planetRepository;    

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public Planet create(Planet planet) {
        return planetRepository.save(planet);
    }

    public Planet find(Long id) {     
        return planetRepository.findById(id).orElseThrow(() -> new RuntimeException("Planet not found")) ;
    }

    public Planet findByName(String name) {
       return planetRepository.findByName(name).orElseThrow(() -> new RuntimeException("Planet not found")) ;
    }
    
}
