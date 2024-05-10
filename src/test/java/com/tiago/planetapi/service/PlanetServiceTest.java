package com.tiago.planetapi.service;

import static com.tiago.planetapi.common.PlanetConstants.PLANET;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.tiago.planetapi.domain.Planet;
import com.tiago.planetapi.repository.PlanetRepository;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {
    
    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository repository;

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){
        when(repository.save(PLANET)).thenReturn(PLANET);
        Planet actual = planetService.create(PLANET);
        assertThat(actual).isEqualTo(PLANET);
    }

}
