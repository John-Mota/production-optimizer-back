
package com.factory.production_optimizer.controller.impl;

import com.factory.production_optimizer.controller.OptimizationController;
import com.factory.production_optimizer.dto.OptimizationResultDTO;
import com.factory.production_optimizer.service.OptimizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OptimizationControllerImpl implements OptimizationController {

    private final OptimizationService optimizationService;

    public OptimizationControllerImpl(OptimizationService optimizationService) {
        this.optimizationService = optimizationService;
    }

    @Override
    public ResponseEntity<OptimizationResultDTO> optimizeProduction() {
        return ResponseEntity.ok(optimizationService.calculateMaxProfitProduction());
    }
}
