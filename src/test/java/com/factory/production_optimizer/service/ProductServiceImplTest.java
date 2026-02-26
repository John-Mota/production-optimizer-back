
package com.factory.production_optimizer.service;

import com.factory.production_optimizer.domain.Product;
import com.factory.production_optimizer.domain.RawMaterial;
import com.factory.production_optimizer.dto.CompositionItemDTO;
import com.factory.production_optimizer.dto.ProductRequestDTO;
import com.factory.production_optimizer.dto.ProductResponseDTO;
import com.factory.production_optimizer.repository.ProductRepository;
import com.factory.production_optimizer.repository.RawMaterialRepository;
import com.factory.production_optimizer.service.impl.ProductServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void create_shouldReturnCreatedProduct() {
        // Arrange
        UUID woodId = UUID.randomUUID();
        CompositionItemDTO compositionItem = new CompositionItemDTO(woodId, new BigDecimal("10"));
        ProductRequestDTO request = new ProductRequestDTO("Table", new BigDecimal("200"), List.of(compositionItem));

        RawMaterial wood = new RawMaterial(woodId, "Wood", new BigDecimal("100"));
        when(rawMaterialRepository.findById(woodId)).thenReturn(Optional.of(wood));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            p.setId(UUID.randomUUID());
            return p;
        });

        // Act
        ProductResponseDTO response = productService.create(request);

        // Assert
        assertNotNull(response);
        assertEquals("Table", response.name());
        assertEquals(1, response.composition().size());
        assertEquals(woodId, response.composition().get(0).rawMaterialId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void create_shouldThrowEntityNotFoundException_whenRawMaterialNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        CompositionItemDTO compositionItem = new CompositionItemDTO(nonExistentId, new BigDecimal("10"));
        ProductRequestDTO request = new ProductRequestDTO("Table", new BigDecimal("200"), List.of(compositionItem));
        when(rawMaterialRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productService.create(request));
    }

    @Test
    void findAll_shouldReturnListOfProducts() {
        // Arrange
        Product product1 = new Product(UUID.randomUUID(), "Table", new BigDecimal("200"), new ArrayList<>());
        Product product2 = new Product(UUID.randomUUID(), "Chair", new BigDecimal("80"), new ArrayList<>());
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // Act
        List<ProductResponseDTO> response = productService.findAll();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    void update_shouldReturnUpdatedProduct() {
        // Arrange
        UUID productId = UUID.randomUUID();
        UUID woodId = UUID.randomUUID();
        CompositionItemDTO newComposition = new CompositionItemDTO(woodId, new BigDecimal("25"));
        ProductRequestDTO request = new ProductRequestDTO("Big Table", new BigDecimal("300"), List.of(newComposition));

        Product existingProduct = new Product(productId, "Table", new BigDecimal("200"), new ArrayList<>());
        RawMaterial wood = new RawMaterial(woodId, "Wood", new BigDecimal("100"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(rawMaterialRepository.findById(woodId)).thenReturn(Optional.of(wood));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ProductResponseDTO response = productService.update(productId, request);

        // Assert
        assertNotNull(response);
        assertEquals("Big Table", response.name());
        assertEquals(new BigDecimal("300"), response.salePrice());
        assertEquals(1, response.composition().size());
        assertEquals(woodId, response.composition().get(0).rawMaterialId());
        assertEquals(new BigDecimal("25"), response.composition().get(0).quantity());
    }

    @Test
    void delete_shouldCompleteSuccessfully_whenFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(productRepository.existsById(id)).thenReturn(true);
        doNothing().when(productRepository).deleteById(id);

        // Act & Assert
        assertDoesNotThrow(() -> productService.delete(id));
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void delete_shouldThrowEntityNotFoundException_whenNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(productRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> productService.delete(id));
    }
}
