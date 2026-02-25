
package com.factory.production_optimizer.controller;

import com.factory.production_optimizer.dto.OptimizationResultDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Otimização de Produção", description = "API para otimização da produção")
@RequestMapping("/optimization")
public interface OptimizationController {

    @Operation(summary = "Calcula o plano de produção de lucro máximo")
    @GetMapping("/optimize")
    ResponseEntity<OptimizationResultDTO> optimizeProduction();
}
