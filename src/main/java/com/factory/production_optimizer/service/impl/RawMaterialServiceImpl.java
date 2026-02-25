
package com.factory.production_optimizer.service.impl;

import com.factory.production_optimizer.domain.RawMaterial;
import com.factory.production_optimizer.dto.RawMaterialRequestDTO;
import com.factory.production_optimizer.dto.RawMaterialResponseDTO;
import com.factory.production_optimizer.repository.RawMaterialRepository;
import com.factory.production_optimizer.service.RawMaterialService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RawMaterialServiceImpl implements RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialServiceImpl(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Override
    @Transactional
    public RawMaterialResponseDTO create(RawMaterialRequestDTO requestDTO) {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName(requestDTO.name());
        rawMaterial.setStockQuantity(requestDTO.stockQuantity());
        RawMaterial savedRawMaterial = rawMaterialRepository.save(rawMaterial);
        return toResponseDTO(savedRawMaterial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RawMaterialResponseDTO> findAll() {
        return rawMaterialRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RawMaterialResponseDTO update(UUID id, RawMaterialRequestDTO requestDTO) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matéria-prima não encontrada com o id: " + id));
        rawMaterial.setName(requestDTO.name());
        rawMaterial.setStockQuantity(requestDTO.stockQuantity());
        RawMaterial updatedRawMaterial = rawMaterialRepository.save(rawMaterial);
        return toResponseDTO(updatedRawMaterial);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!rawMaterialRepository.existsById(id)) {
            throw new EntityNotFoundException("Matéria-prima não encontrada com o id: " + id);
        }
        rawMaterialRepository.deleteById(id);
    }

    private RawMaterialResponseDTO toResponseDTO(RawMaterial rawMaterial) {
        return new RawMaterialResponseDTO(
                rawMaterial.getId(),
                rawMaterial.getName(),
                rawMaterial.getStockQuantity()
        );
    }
}
