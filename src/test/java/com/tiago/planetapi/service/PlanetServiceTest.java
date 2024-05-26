package com.tiago.planetapi.service;

import static com.tiago.planetapi.common.PlanetConstants.EMPTY_PLANET;
import static com.tiago.planetapi.common.PlanetConstants.LIST_PLANETS;
import static com.tiago.planetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import com.tiago.planetapi.domain.Planet;
import com.tiago.planetapi.exception.PlanetNotFoundException;
import com.tiago.planetapi.repository.PlanetRepository;
import com.tiago.planetapi.utils.QueryBuilder;

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
        when(repository.save(EMPTY_PLANET)).thenThrow(PlanetNotFoundException.class);
        assertThatThrownBy(() -> planetService.create(EMPTY_PLANET)).isInstanceOf(PlanetNotFoundException.class);
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
        assertThatThrownBy(() -> planetService.find(0L)).isInstanceOf(PlanetNotFoundException.class).hasMessage("Planet not found");
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
        assertThatThrownBy(() -> planetService.findByName("")).isInstanceOf(PlanetNotFoundException.class).hasMessage("Planet not found");
    }

    @Test
    public void listPlanet_WithoutFilter_ReturnsListAllPlanets(){
        Example<Planet> example = QueryBuilder.makeQuery(Planet.builder().climate(null).terrain(null).build());
        when(repository.findAll(example)).thenReturn(LIST_PLANETS);
        var actual = planetService.list(null, null);
        assertThat(actual).isNotEmpty();
        assertThat(actual).hasSize(5);        
    }


    @Test
    public void listPlanet_WithFilter_ReturnsListPlanetsFiltered(){
        final String climate = "Cold";
        final String terrain = "Gaseous";
        Example<Planet> example = QueryBuilder.makeQuery(Planet.builder().climate(climate).terrain(terrain).build());
        when(repository.findAll(example)).thenReturn(getListPlanetByTerrain(getListPlanetByClimate(LIST_PLANETS, climate), terrain));
        var actual = planetService.list(climate, terrain);
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getName()).isEqualTo("Pluto");
    } 

    @Test
    public void deletePlanet_WithExistingId_DoesNotThrowAnyException(){
        when(repository.findById(1L)).thenReturn(Optional.of(PLANET));
        doNothing().when(repository).delete(PLANET);
        assertThatCode(() -> planetService.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    public void deletePlanet_WithUnexisting_ThrowsException(){
        when(repository.findById(0L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> planetService.delete(0L)).isInstanceOf(PlanetNotFoundException.class).hasMessage("Planet not found");
    }

    private List<Planet> getListPlanetByClimate(final List<Planet> listPlanets, final String pClimate){
       return listPlanets.stream().filter(p -> p.getClimate().equals(pClimate)).toList();
    }

    private List<Planet> getListPlanetByTerrain(final List<Planet> listPlanets, final String pTerrain){
        return listPlanets.stream().filter(p -> p.getTerrain().equals(pTerrain)).toList();
     }


}
