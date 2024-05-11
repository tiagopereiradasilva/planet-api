package com.tiago.planetapi.service;

import static com.tiago.planetapi.common.PlanetConstants.PLANET;
import static com.tiago.planetapi.common.PlanetConstants.INVALID_PLANET;
import static com.tiago.planetapi.common.PlanetConstants.ID;
import static com.tiago.planetapi.common.PlanetConstants.UNEXISTING_ID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

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

    @Test
    public void createPlanet_WithInvalidData_ReturnsException(){
        when(repository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    
    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet(){
        when(repository.findById(ID)).thenReturn(Optional.of(PLANET));
        Planet actual = planetService.find(ID);
        assertThat(actual).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsException(){
        when(repository.findById(UNEXISTING_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> planetService.find(UNEXISTING_ID)).isInstanceOf(RuntimeException.class).hasMessage("Planet not found");
    }

}
