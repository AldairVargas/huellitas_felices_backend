package com.example.huellitas_felices.controller;

import com.example.huellitas_felices.dto.CategoryDTO;
import com.example.huellitas_felices.model.Category;
import com.example.huellitas_felices.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Controlador de categorías", description = "Gestión de categorías de mascotas")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasRole('EMPLEADO') or hasRole('SUPERADMIN')")
    @Operation(summary = "Obtener todas las categorías", description = "Lista todas las categorías de mascotas disponibles")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autorizado - Token inválido",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Prohibido - Se requiere rol EMPLEADO o SUPERADMIN",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            )
    })
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    @Operation(summary = "Crear categoría", description = "Registro de una nueva categoría de mascota")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoría creada exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Category.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error en los datos proporcionados",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "No autorizado - Token inválido",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Prohibido - Se requiere rol SUPERADMIN",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            )
    })
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.createCategory(dto));
    }
}