
package com.factory.production_optimizer.dto;

import com.factory.production_optimizer.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionSuggestionDTO {
    private Product product;
    private Integer quantity;
}
