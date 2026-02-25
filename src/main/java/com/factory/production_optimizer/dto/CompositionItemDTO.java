
package com.factory.production_optimizer.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CompositionItemDTO(
        @NotNull UUID rawMaterialId,
        @NotNull @Positive BigDecimal quantity
) {
}
