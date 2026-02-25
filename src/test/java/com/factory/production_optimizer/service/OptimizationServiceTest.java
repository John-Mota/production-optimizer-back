
package com.factory.production_optimizer.service;

import com.factory.production_optimizer.domain.Product;
import com.factory.production_optimizer.domain.ProductComposition;
import com.factory.production_optimizer.domain.RawMaterial;
import com.factory.production_optimizer.dto.OptimizationResultDTO;
import com.factory.production_optimizer.repository.ProductRepository;
import com.factory.production_optimizer.repository.RawMaterialRepository;
import com.factory.production_optimizer.service.impl.OptimizationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OptimizationServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private OptimizationServiceImpl optimizationService;

    @Test
    void calculateMaxProfitProduction_shouldPrioritizeHigherValueProduct() {
        // Arrange
        RawMaterial wood = new RawMaterial(UUID.randomUUID(), "Wood", new BigDecimal("100"));
        RawMaterial steel = new RawMaterial(UUID.randomUUID(), "Steel", new BigDecimal("50"));

        Product chair = new Product(UUID.randomUUID(), "Chair", new BigDecimal("50"), Collections.emptyList());
        Product table = new Product(UUID.randomUUID(), "Table", new BigDecimal("200"), Collections.emptyList());

        ProductComposition chairComposition = new ProductComposition(1L, chair, wood, new BigDecimal("5"));
        ProductComposition tableComposition = new ProductComposition(2L, table, wood, new BigDecimal("20"));
        ProductComposition tableSteelComposition = new ProductComposition(3L, table, steel, new BigDecimal("10"));

        chair.setComposition(Collections.singletonList(chairComposition));
        table.setComposition(Arrays.asList(tableComposition, tableSteelComposition));

        List<Product> products = Arrays.asList(table, chair);
        List<RawMaterial> rawMaterials = Arrays.asList(wood, steel);

        when(productRepository.findAllByOrderBySalePriceDesc()).thenReturn(products);
        when(rawMaterialRepository.findAll()).thenReturn(rawMaterials);

        // Act
        OptimizationResultDTO result = optimizationService.calculateMaxProfitProduction();

        // Assert
        // The table should be prioritized, producing 5 units (100 wood / 20 per table)
        assertEquals("Table", result.getProductionSuggestions().get(0).getProduct().getName());
        assertEquals(5, result.getProductionSuggestions().get(0).getQuantity());

        // After making 5 tables, there is no wood left (5 * 20 = 100). So, 0 chairs can be made.
        assertEquals("Chair", result.getProductionSuggestions().get(1).getProduct().getName());
        assertEquals(0, result.getProductionSuggestions().get(1).getQuantity());

        // Total value should be 5 tables * 200/table = 1000
        assertEquals(new BigDecimal("1000.00"), result.getTotalProjectedValue().setScale(2));
    }
}
