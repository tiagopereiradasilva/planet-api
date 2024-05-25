package com.tiago.planetapi.repository;

import static com.tiago.planetapi.common.PlanetConstants.EMPTY_PLANET;
import static com.tiago.planetapi.common.PlanetConstants.PLANET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.tiago.planetapi.domain.Planet;

// Anotação para utilizar banco em memória (com o H2) e aplicar o contexto do spring para o "PlanetRepository" ser executado devidamente
@DataJpaTest
public class PlanetRepositoryTest {
    
    @Autowired
    private PlanetRepository repository;

    @Autowired
    private TestEntityManager entityManager; // Entidade para fazer buscas no banco e validar se a "integração" está funcionando, dessa forma o teste será validado.

    @Test
    public void savePlanet_WithValidData_ReturnsPlanet(){
        Planet planet = repository.save(PLANET);
        Planet actual = entityManager.find(Planet.class, planet.getId());
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(PLANET.getName());
        assertThat(actual.getClimate()).isEqualTo(PLANET.getClimate());
        assertThat(actual.getTerrain()).isEqualTo(PLANET.getTerrain());  
    }

    @Test
    public void savePlanet_WithInvalidData_ThrowsException(){        
        Planet invalidPlanet = new Planet();       
        assertThatThrownBy(() -> repository.save(invalidPlanet));
        assertThatThrownBy(() -> repository.save(EMPTY_PLANET));
    }

    @Test
    public void savePlane_WithExistingName_ThrowsException(){
        Planet planet = new Planet("Pluto", "Temperate", "Rocky");
        Planet planetSaved = entityManager.persistFlushFind(planet);
        entityManager.detach(planetSaved); // Tal método deve ser chamado para remover o mapeamento do objeto, de forma que o JPA tentará criar um novo e não atualizar um já criado.
        planetSaved.setId(null);
        assertThatThrownBy(() -> repository.save(planetSaved));
    }


}
