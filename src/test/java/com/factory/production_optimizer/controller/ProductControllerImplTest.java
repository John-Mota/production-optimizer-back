
package com.factory.production_optimizer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.factory.production_optimizer.dto.CompositionItemDTO;
import com.factory.production_optimizer.dto.ProductRequestDTO;
import com.factory.production_optimizer.dto.ProductResponseDTO;
import com.factory.production_optimizer.service.ProductService;
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

@WebMvcTest(controllers = com.factory.production_optimizer.controller.impl.ProductControllerImpl.class)
class ProductControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void create_shouldReturn201_whenRequestIsValid() throws Exception {
        CompositionItemDTO composition = new CompositionItemDTO(UUID.randomUUID(), new BigDecimal("10"));
        ProductRequestDTO request = new ProductRequestDTO("Table", new BigDecimal("200"), List.of(composition));
        ProductResponseDTO response = new ProductResponseDTO(UUID.randomUUID(), "Table", new BigDecimal("200"), List.of(composition));

        when(productService.create(any(ProductRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Table"));
    }

    @Test
    void findAll_shouldReturn200_withListOfProducts() throws Exception {
        ProductResponseDTO response1 = new ProductResponseDTO(UUID.randomUUID(), "Table", new BigDecimal("200"), List.of());
        ProductResponseDTO response2 = new ProductResponseDTO(UUID.randomUUID(), "Chair", new BigDecimal("80"), List.of());

        when(productService.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void update_shouldReturn200_whenRequestIsValid() throws Exception {
        UUID id = UUID.randomUUID();
        ProductRequestDTO request = new ProductRequestDTO("Updated Table", new BigDecimal("250"), List.of());
        ProductResponseDTO response = new ProductResponseDTO(id, "Updated Table", new BigDecimal("250"), List.of());

        when(productService.update(eq(id), any(ProductRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/products/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Table"));
    }

    @Test
    void delete_shouldReturn204_whenIdExists() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/products/{id}", id))
                .andExpect(status().isNoContent());
    }
}
