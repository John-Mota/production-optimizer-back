
package com.factory.production_optimizer.service.impl;

import com.factory.production_optimizer.domain.Product;
import com.factory.production_optimizer.domain.ProductComposition;
import com.factory.production_optimizer.domain.RawMaterial;
import com.factory.production_optimizer.dto.OptimizationResultDTO;
import com.factory.production_optimizer.dto.ProductionSuggestionDTO;
import com.factory.production_optimizer.repository.ProductRepository;
import com.factory.production_optimizer.repository.RawMaterialRepository;
import com.factory.production_optimizer.service.OptimizationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OptimizationServiceImpl implements OptimizationService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public OptimizationServiceImpl(ProductRepository productRepository, RawMaterialRepository rawMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Override
    public OptimizationResultDTO calculateMaxProfitProduction() {
        List<Product> products = productRepository.findAllByOrderBySalePriceDesc();
        Map<UUID, BigDecimal> availableStock = rawMaterialRepository.findAll().stream()
                .collect(Collectors.toMap(RawMaterial::getId, RawMaterial::getStockQuantity));

        List<ProductionSuggestionDTO> suggestions = new ArrayList<>();
        BigDecimal totalProjectedValue = BigDecimal.ZERO;

        for (Product product : products) {
            int maxPossibleUnits = calculateMaxPossibleUnits(product, availableStock);

            suggestions.add(new ProductionSuggestionDTO(product, maxPossibleUnits));
            if (maxPossibleUnits > 0) {
                totalProjectedValue = totalProjectedValue.add(product.getSalePrice().multiply(BigDecimal.valueOf(maxPossibleUnits)));
                updateStock(product, availableStock, maxPossibleUnits);
            }
        }

        return new OptimizationResultDTO(suggestions, totalProjectedValue);
    }

    private int calculateMaxPossibleUnits(Product product, Map<UUID, BigDecimal> availableStock) {
        int maxPossibleUnits = Integer.MAX_VALUE;

        for (ProductComposition composition : product.getComposition()) {
            RawMaterial rawMaterial = composition.getRawMaterial();
            BigDecimal requiredQuantity = composition.getQuantity();
            BigDecimal stockQuantity = availableStock.get(rawMaterial.getId());

            if (requiredQuantity.compareTo(BigDecimal.ZERO) > 0) {
                int possibleUnits = stockQuantity.divide(requiredQuantity, 0, RoundingMode.DOWN).intValue();
                if (possibleUnits < maxPossibleUnits) {
                    maxPossibleUnits = possibleUnits;
                }
            }
        }

        return maxPossibleUnits == Integer.MAX_VALUE ? 0 : maxPossibleUnits;
    }

    private void updateStock(Product product, Map<UUID, BigDecimal> availableStock, int unitsToProduce) {
        for (ProductComposition composition : product.getComposition()) {
            RawMaterial rawMaterial = composition.getRawMaterial();
            BigDecimal requiredQuantity = composition.getQuantity();
            BigDecimal totalRequired = requiredQuantity.multiply(BigDecimal.valueOf(unitsToProduce));
            availableStock.computeIfPresent(rawMaterial.getId(), (key, stock) -> stock.subtract(totalRequired));
        }
    }
}
