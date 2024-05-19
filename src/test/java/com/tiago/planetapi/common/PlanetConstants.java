package com.tiago.planetapi.common;

import java.util.List;

import com.tiago.planetapi.domain.Planet;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("planet", "climate", "terrain");
    public static final Planet EMPTY_PLANET = new Planet("", "", "");
    public static final List<Planet> LIST_PLANETS = List.of(
        PLANET,
        new Planet("Earth", "Temperate", "Rocky"),
        new Planet("Pluto", "Cold", "Gaseous"),
        new Planet("Saturn", "Cold", "Rocky"),
        new Planet("Mars", "Temperate", "Rocky")
    );
}
