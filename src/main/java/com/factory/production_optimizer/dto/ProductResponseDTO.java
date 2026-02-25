
package com.factory.production_optimizer.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        BigDecimal salePrice,
        List<CompositionItemDTO> composition
) {
}
