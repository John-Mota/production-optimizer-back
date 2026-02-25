
package com.factory.production_optimizer.service.impl;

import com.factory.production_optimizer.domain.Product;
import com.factory.production_optimizer.domain.ProductComposition;
import com.factory.production_optimizer.domain.RawMaterial;
import com.factory.production_optimizer.dto.CompositionItemDTO;
import com.factory.production_optimizer.dto.ProductRequestDTO;
import com.factory.production_optimizer.dto.ProductResponseDTO;
import com.factory.production_optimizer.repository.ProductRepository;
import com.factory.production_optimizer.repository.RawMaterialRepository;
import com.factory.production_optimizer.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductServiceImpl(ProductRepository productRepository, RawMaterialRepository rawMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Override
    @Transactional
    public ProductResponseDTO create(ProductRequestDTO requestDTO) {
        Product product = new Product();
        product.setName(requestDTO.name());
        product.setSalePrice(requestDTO.salePrice());

        List<ProductComposition> composition = requestDTO.composition().stream()
                .map(item -> {
                    RawMaterial rawMaterial = rawMaterialRepository.findById(item.rawMaterialId())
                            .orElseThrow(() -> new EntityNotFoundException("RawMaterial not found with id: " + item.rawMaterialId()));
                    ProductComposition pc = new ProductComposition();
                    pc.setProduct(product);
                    pc.setRawMaterial(rawMaterial);
                    pc.setQuantity(item.quantity());
                    return pc;
                })
                .collect(Collectors.toList());

        product.setComposition(composition);
        Product savedProduct = productRepository.save(product);
        return toResponseDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    private ProductResponseDTO toResponseDTO(Product product) {
        List<CompositionItemDTO> compositionDTO = product.getComposition().stream()
                .map(c -> new CompositionItemDTO(c.getRawMaterial().getId(), c.getQuantity()))
                .collect(Collectors.toList());

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getSalePrice(),
                compositionDTO
        );
    }
}
