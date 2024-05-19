package com.tiago.planetapi.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.tiago.planetapi.domain.Planet;
import com.tiago.planetapi.repository.PlanetRepository;
import com.tiago.planetapi.utils.QueryBuilder;

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

    public List<Planet> list(String climate, String terrain) {        
        Example<Planet> example = QueryBuilder.makeQuery(Planet.builder().climate(climate).terrain(terrain).build());
        return planetRepository.findAll(example);
    }

    public void delete(Long id) {
       Planet planet = planetRepository.findById(id).orElseThrow(() -> new RuntimeException("Planet not found")) ;
       planetRepository.delete(planet);
    }
    
}
