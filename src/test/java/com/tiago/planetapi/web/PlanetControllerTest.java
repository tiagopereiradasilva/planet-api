package com.tiago.planetapi.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiago.planetapi.domain.Planet;
import com.tiago.planetapi.service.PlanetService;

import static com.tiago.planetapi.common.PlanetConstants.EMPTY_PLANET;
import static com.tiago.planetapi.common.PlanetConstants.PLANET;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PlanetController.class) // Injeta contexto web para o teste de forma que possamos acessar a API.
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Anotação para mockar entidade gerenciada pelo Spring
    PlanetService planetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("Create Planet Test")
    class CreatePlanetTest {
        @Test
        public void createPlanet_WithValidData_ReturnsCreated() throws Exception {
            when(planetService.create(PLANET)).thenReturn(PLANET);
            mockMvc.perform(
                    post("/planets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(PLANET)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$").value(PLANET));
        }

        @Test
        public void createPlanet_WithInvalidData_ReturnsBadRequest() throws Exception {

            Planet invalidPlanet = new Planet();

            mockMvc.perform(
                    post("/planets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidPlanet)))
                    .andExpect(status().isUnprocessableEntity());

            mockMvc.perform(
                    post("/planets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(EMPTY_PLANET)))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        public void createPlanet_WithExistingname_ReturnsConflict() throws Exception {
            when(planetService.create(any())).thenThrow(DataIntegrityViolationException.class);
            mockMvc.perform(
                    post("/planets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(PLANET)))
                    .andExpect(status().isConflict());
        }
    }
}
