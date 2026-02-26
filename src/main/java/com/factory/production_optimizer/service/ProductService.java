
package com.factory.production_optimizer.service;

import com.factory.production_optimizer.dto.ProductRequestDTO;
import com.factory.production_optimizer.dto.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO requestDTO);
    List<ProductResponseDTO> findAll();
    ProductResponseDTO update(UUID id, ProductRequestDTO requestDTO);
    void delete(UUID id);
}
