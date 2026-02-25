
package com.factory.production_optimizer.controller;

import com.factory.production_optimizer.dto.ProductRequestDTO;
import com.factory.production_optimizer.dto.ProductResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Products", description = "API for managing products")
@RequestMapping("/api/products")
public interface ProductController {

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO requestDTO);

    @Operation(summary = "List all products")
    @GetMapping
    ResponseEntity<List<ProductResponseDTO>> findAll();

    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
