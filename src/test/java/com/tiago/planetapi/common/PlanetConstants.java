package com.tiago.planetapi.common;

import com.tiago.planetapi.domain.Planet;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("planet", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet("", "", "");
    public static final Long ID = 1L;
    public static final Long UNEXISTING_ID = 0L;
}
