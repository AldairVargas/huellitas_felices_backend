package com.example.huellitas_felices.controller;

import com.example.huellitas_felices.dto.PetRegisterDTO;
import com.example.huellitas_felices.dto.PetStatusUpdateDTO;
import com.example.huellitas_felices.model.Pet;
import com.example.huellitas_felices.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/pets")
@RequiredArgsConstructor
@Tag(name = "Controlador de mascotas", description = "Gestión de mascotas del refugio")
public class PetController {

    private final PetService petService;

    @GetMapping
    @Operation(summary = "Obtener todas las mascotas", description = "Lista todas las mascotas con filtros opcionales por categoría, estado y raza")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))
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
    public ResponseEntity<List<Pet>> getAllPets(
            @Parameter(description = "Filtrar por nombre de categoría") @RequestParam(required = false) String categoria,
            @Parameter(description = "Filtrar por estado de la mascota") @RequestParam(required = false) String estado,
            @Parameter(description = "Filtrar por raza de la mascota") @RequestParam(required = false) String raza
    ) {
        return ResponseEntity.ok(petService.getAllPets(categoria, estado, raza));
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLEADO') or hasRole('SUPERADMIN')")
    @Operation(summary = "Registrar nueva mascota", description = "Registro de una nueva mascota en el refugio")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Mascota registrada exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))
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
    public ResponseEntity<Pet> registerPet(@Valid @RequestBody PetRegisterDTO dto) {
        return ResponseEntity.ok(petService.registerPet(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLEADO') or hasRole('SUPERADMIN')")
    @Operation(summary = "Actualizar estado de mascota", description = "Actualiza el estado de una mascota específica")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estado actualizado exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))
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
                    description = "Prohibido - Se requiere rol EMPLEADO o SUPERADMIN",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mascota no encontrada",
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
    public ResponseEntity<Pet> updatePet(
            @Parameter(description = "ID de la mascota a actualizar") @PathVariable Long id, 
            @Valid @RequestBody PetStatusUpdateDTO dto) {
        return ResponseEntity.ok(petService.updatePet(id, dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener mascota por ID", description = "Consulta una mascota específica por su ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Mascota encontrada exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Pet.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Mascota no encontrada",
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
    public ResponseEntity<Pet> getPetById(
            @Parameter(description = "ID de la mascota a consultar") @PathVariable Long id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }
}