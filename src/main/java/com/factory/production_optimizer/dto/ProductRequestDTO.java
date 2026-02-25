
package com.factory.production_optimizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequestDTO(
        @NotBlank String name,
        @NotNull @Positive BigDecimal salePrice,
        List<CompositionItemDTO> composition
) {
}
