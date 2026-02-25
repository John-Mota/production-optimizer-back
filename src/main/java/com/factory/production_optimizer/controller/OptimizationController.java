
package com.factory.production_optimizer.controller;

import com.factory.production_optimizer.dto.OptimizationResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Production Optimization", description = "API for production optimization")
@RequestMapping("/api/optimization")
public interface OptimizationController {

    @Operation(summary = "Calculate the maximum profit production plan")
    @GetMapping("/optimize")
    ResponseEntity<OptimizationResultDTO> optimizeProduction();
}
