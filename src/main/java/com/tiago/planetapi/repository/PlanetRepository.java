package com.tiago.planetapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tiago.planetapi.domain.Planet;

public interface PlanetRepository extends JpaRepository<Planet, Long>{

    Optional<Planet> findByName(String string);
    
}
