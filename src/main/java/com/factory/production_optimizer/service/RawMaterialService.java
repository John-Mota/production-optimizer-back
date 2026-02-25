
package com.factory.production_optimizer.service;

import com.factory.production_optimizer.dto.RawMaterialRequestDTO;
import com.factory.production_optimizer.dto.RawMaterialResponseDTO;

import java.util.List;
import java.util.UUID;

public interface RawMaterialService {
    RawMaterialResponseDTO create(RawMaterialRequestDTO requestDTO);
    List<RawMaterialResponseDTO> findAll();
    RawMaterialResponseDTO update(UUID id, RawMaterialRequestDTO requestDTO);
    void delete(UUID id);
}
