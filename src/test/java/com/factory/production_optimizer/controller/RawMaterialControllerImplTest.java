
package com.factory.production_optimizer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.factory.production_optimizer.dto.RawMaterialRequestDTO;
import com.factory.production_optimizer.dto.RawMaterialResponseDTO;
import com.factory.production_optimizer.service.RawMaterialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = com.factory.production_optimizer.controller.impl.RawMaterialControllerImpl.class)
class RawMaterialControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RawMaterialService rawMaterialService;

    @Test
    void create_shouldReturn201_whenRequestIsValid() throws Exception {
        RawMaterialRequestDTO request = new RawMaterialRequestDTO("Wood", new BigDecimal("100"));
        RawMaterialResponseDTO response = new RawMaterialResponseDTO(UUID.randomUUID(), "Wood", new BigDecimal("100"));

        when(rawMaterialService.create(any(RawMaterialRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Wood"));
    }

    @Test
    void create_shouldReturn400_whenRequestIsInvalid() throws Exception {
        // Invalid request with blank name
        RawMaterialRequestDTO request = new RawMaterialRequestDTO("", new BigDecimal("100"));

        mockMvc.perform(post("/materials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAll_shouldReturn200_withListOfMaterials() throws Exception {
        RawMaterialResponseDTO response1 = new RawMaterialResponseDTO(UUID.randomUUID(), "Wood", new BigDecimal("100"));
        RawMaterialResponseDTO response2 = new RawMaterialResponseDTO(UUID.randomUUID(), "Steel", new BigDecimal("50"));

        when(rawMaterialService.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/materials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void update_shouldReturn200_whenRequestIsValid() throws Exception {
        UUID id = UUID.randomUUID();
        RawMaterialRequestDTO request = new RawMaterialRequestDTO("Updated Wood", new BigDecimal("150"));
        RawMaterialResponseDTO response = new RawMaterialResponseDTO(id, "Updated Wood", new BigDecimal("150"));

        when(rawMaterialService.update(eq(id), any(RawMaterialRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/materials/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Wood"));
    }

    @Test
    void delete_shouldReturn204_whenIdExists() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/materials/{id}", id))
                .andExpect(status().isNoContent());
    }
}
