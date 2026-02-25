
package com.factory.production_optimizer.controller.impl;

import com.factory.production_optimizer.controller.ProductController;
import com.factory.production_optimizer.dto.ProductRequestDTO;
import com.factory.production_optimizer.dto.ProductResponseDTO;
import com.factory.production_optimizer.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    public ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductResponseDTO> create(ProductRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(requestDTO));
    }

    @Override
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
