package com.tiago.planetapi.utils;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.tiago.planetapi.domain.Planet;

public class QueryBuilder {
    public static Example<Planet> makeQuery(final Planet pPlanet){
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
        return Example.of(pPlanet, exampleMatcher);
    }
}
