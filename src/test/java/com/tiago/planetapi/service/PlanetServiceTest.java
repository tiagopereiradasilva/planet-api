package com.tiago.planetapi.service;

import static com.tiago.planetapi.common.PlanetConstants.PLANET;
import static com.tiago.planetapi.common.PlanetConstants.INVALID_PLANET;

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
        when(repository.findById(1L)).thenReturn(Optional.of(PLANET));
        Planet actual = planetService.find(1L);
        assertThat(actual).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsException(){
        when(repository.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> planetService.find(0L)).isInstanceOf(RuntimeException.class).hasMessage("Planet not found");
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet(){
        when(repository.findByName("planet")).thenReturn(Optional.of(PLANET));
        Planet actual = planetService.findByName("planet");
        assertThat(actual).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsException(){
        when(repository.findByName("")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> planetService.findByName("")).isInstanceOf(RuntimeException.class).hasMessage("Planet not found");
    }

}
