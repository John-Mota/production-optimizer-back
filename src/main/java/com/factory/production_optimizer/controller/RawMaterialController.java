
package com.factory.production_optimizer.controller;

import com.factory.production_optimizer.dto.RawMaterialRequestDTO;
import com.factory.production_optimizer.dto.RawMaterialResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Raw Materials", description = "API for managing raw materials")
@RequestMapping("/api/materials")
public interface RawMaterialController {

    @Operation(summary = "Create a new raw material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Raw material created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    ResponseEntity<RawMaterialResponseDTO> create(@Valid @RequestBody RawMaterialRequestDTO requestDTO);

    @Operation(summary = "List all raw materials")
    @GetMapping
    ResponseEntity<List<RawMaterialResponseDTO>> findAll();

    @Operation(summary = "Update an existing raw material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Raw material updated successfully"),
            @ApiResponse(responseCode = "404", description = "Raw material not found")
    })
    @PutMapping("/{id}")
    ResponseEntity<RawMaterialResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody RawMaterialRequestDTO requestDTO);

    @Operation(summary = "Delete a raw material")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Raw material deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Raw material not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
