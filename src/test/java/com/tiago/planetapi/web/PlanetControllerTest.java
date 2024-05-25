package com.tiago.planetapi.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiago.planetapi.service.PlanetService;

import static com.tiago.planetapi.common.PlanetConstants.PLANET;
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

    @Test
    public void createPlane_WithValidData_ReturnsCreated() throws Exception {
        when(planetService.create(PLANET)).thenReturn(PLANET);
        mockMvc.perform(
            post("/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(PLANET))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath( "$").value(PLANET));
    }    
}
