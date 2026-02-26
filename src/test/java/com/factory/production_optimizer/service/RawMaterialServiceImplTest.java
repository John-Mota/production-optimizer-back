
package com.factory.production_optimizer.service;

import com.factory.production_optimizer.domain.RawMaterial;
import com.factory.production_optimizer.dto.RawMaterialRequestDTO;
import com.factory.production_optimizer.dto.RawMaterialResponseDTO;
import com.factory.production_optimizer.repository.RawMaterialRepository;
import com.factory.production_optimizer.service.impl.RawMaterialServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RawMaterialServiceImplTest {

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private RawMaterialServiceImpl rawMaterialService;

    @Test
    void create_shouldReturnCreatedRawMaterial() {
        // Arrange
        RawMaterialRequestDTO request = new RawMaterialRequestDTO("Wood", new BigDecimal("100"));
        RawMaterial savedMaterial = new RawMaterial(UUID.randomUUID(), "Wood", new BigDecimal("100"));
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenReturn(savedMaterial);

        // Act
        RawMaterialResponseDTO response = rawMaterialService.create(request);

        // Assert
        assertNotNull(response);
        assertEquals("Wood", response.name());
        assertEquals(new BigDecimal("100"), response.stockQuantity());
        verify(rawMaterialRepository, times(1)).save(any(RawMaterial.class));
    }

    @Test
    void findAll_shouldReturnListOfRawMaterials() {
        // Arrange
        RawMaterial material1 = new RawMaterial(UUID.randomUUID(), "Wood", new BigDecimal("100"));
        RawMaterial material2 = new RawMaterial(UUID.randomUUID(), "Steel", new BigDecimal("50"));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(material1, material2));

        // Act
        List<RawMaterialResponseDTO> response = rawMaterialService.findAll();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    void update_shouldReturnUpdatedRawMaterial_whenFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        RawMaterialRequestDTO request = new RawMaterialRequestDTO("Updated Wood", new BigDecimal("150"));
        RawMaterial existingMaterial = new RawMaterial(id, "Wood", new BigDecimal("100"));
        when(rawMaterialRepository.findById(id)).thenReturn(Optional.of(existingMaterial));
        when(rawMaterialRepository.save(any(RawMaterial.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        RawMaterialResponseDTO response = rawMaterialService.update(id, request);

        // Assert
        assertNotNull(response);
        assertEquals("Updated Wood", response.name());
        assertEquals(new BigDecimal("150"), response.stockQuantity());
    }

    @Test
    void update_shouldThrowEntityNotFoundException_whenNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        RawMaterialRequestDTO request = new RawMaterialRequestDTO("Updated Wood", new BigDecimal("150"));
        when(rawMaterialRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> rawMaterialService.update(id, request));
    }

    @Test
    void delete_shouldCompleteSuccessfully_whenFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(rawMaterialRepository.existsById(id)).thenReturn(true);
        doNothing().when(rawMaterialRepository).deleteById(id);

        // Act & Assert
        assertDoesNotThrow(() -> rawMaterialService.delete(id));
        verify(rawMaterialRepository, times(1)).deleteById(id);
    }

    @Test
    void delete_shouldThrowEntityNotFoundException_whenNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(rawMaterialRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> rawMaterialService.delete(id));
        verify(rawMaterialRepository, never()).deleteById(id);
    }
}
