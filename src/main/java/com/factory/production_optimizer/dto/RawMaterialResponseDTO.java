
package com.factory.production_optimizer.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record RawMaterialResponseDTO(
        UUID id,
        String name,
        BigDecimal stockQuantity
) {
}
