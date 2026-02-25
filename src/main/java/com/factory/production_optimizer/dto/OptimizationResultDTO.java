
package com.factory.production_optimizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptimizationResultDTO {
    private List<ProductionSuggestionDTO> productionSuggestions;
    private BigDecimal totalProjectedValue;
}
