
package com.factory.production_optimizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record RawMaterialRequestDTO(
        @NotBlank String name,
        @NotNull @PositiveOrZero BigDecimal stockQuantity
) {
}
