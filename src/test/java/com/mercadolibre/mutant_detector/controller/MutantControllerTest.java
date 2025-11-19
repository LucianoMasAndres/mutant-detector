package com.mercadolibre.mutant_detector.controller;

import com.mercadolibre.mutant_detector.dto.StatsResponse;
import com.mercadolibre.mutant_detector.service.MutantService;
import com.mercadolibre.mutant_detector.service.StatsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // ← IMPORT NUEVO
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ANTES: @MockBean
    // AHORA: @MockitoBean (Hace lo mismo, pero es el estándar nuevo)
    @MockitoBean
    private MutantService mutantService;

    @MockitoBean
    private StatsService statsService;

    @Test
    void checkMutant_ShouldReturn200_WhenMutant() throws Exception {
        when(mutantService.analyzeDna(any())).thenReturn(true);
        String json = "{\"dna\":[\"AAAA\",\"CCCC\",\"TCAG\",\"GGTC\"]}";

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void checkMutant_ShouldReturn403_WhenHuman() throws Exception {
        when(mutantService.analyzeDna(any())).thenReturn(false);
        String json = "{\"dna\":[\"ATCG\",\"ATCG\",\"ATCG\",\"ATCG\"]}";

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    void getStats_ShouldReturn200() throws Exception {
        when(statsService.getStats()).thenReturn(new StatsResponse(1, 1, 1.0));

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk());
    }

    @Test
    void checkMutant_InvalidInput_ShouldReturn400() throws Exception {
        String json = "{}";
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}