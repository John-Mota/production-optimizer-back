
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

@Tag(name = "Matérias-Primas", description = "API para gerenciamento de matérias-primas")
@RequestMapping("/materials")
public interface RawMaterialController {

    @Operation(summary = "Cria uma nova matéria-prima")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Matéria-prima criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping
    ResponseEntity<RawMaterialResponseDTO> create(@Valid @RequestBody RawMaterialRequestDTO requestDTO);

    @Operation(summary = "Lista todas as matérias-primas")
    @GetMapping
    ResponseEntity<List<RawMaterialResponseDTO>> findAll();

    @Operation(summary = "Atualiza uma matéria-prima existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matéria-prima atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Matéria-prima não encontrada")
    })
    @PutMapping("/{id}")
    ResponseEntity<RawMaterialResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody RawMaterialRequestDTO requestDTO);

    @Operation(summary = "Deleta uma matéria-prima")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Matéria-prima deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Matéria-prima não encontrada")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
