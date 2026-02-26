
package com.factory.production_optimizer.controller;

import com.factory.production_optimizer.dto.OptimizationResultDTO;
import com.factory.production_optimizer.service.OptimizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = com.factory.production_optimizer.controller.impl.OptimizationControllerImpl.class)
class OptimizationControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OptimizationService optimizationService;

    @Test
    void optimizeProduction_shouldReturn200_withOptimizationResult() throws Exception {
        OptimizationResultDTO result = new OptimizationResultDTO(List.of(), new BigDecimal("1000.00"));

        when(optimizationService.calculateMaxProfitProduction()).thenReturn(result);

        mockMvc.perform(get("/optimization/optimize"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalProjectedValue").value(1000.00));
    }
}
