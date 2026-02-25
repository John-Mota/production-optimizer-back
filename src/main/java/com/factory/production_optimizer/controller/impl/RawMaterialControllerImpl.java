
package com.factory.production_optimizer.controller.impl;

import com.factory.production_optimizer.controller.RawMaterialController;
import com.factory.production_optimizer.dto.RawMaterialRequestDTO;
import com.factory.production_optimizer.dto.RawMaterialResponseDTO;
import com.factory.production_optimizer.service.RawMaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class RawMaterialControllerImpl implements RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialControllerImpl(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @Override
    public ResponseEntity<RawMaterialResponseDTO> create(RawMaterialRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rawMaterialService.create(requestDTO));
    }

    @Override
    public ResponseEntity<List<RawMaterialResponseDTO>> findAll() {
        return ResponseEntity.ok(rawMaterialService.findAll());
    }

    @Override
    public ResponseEntity<RawMaterialResponseDTO> update(UUID id, RawMaterialRequestDTO requestDTO) {
        return ResponseEntity.ok(rawMaterialService.update(id, requestDTO));
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        rawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
