package com.example.huellitas_felices.controller;

import com.example.huellitas_felices.dto.AdoptionRequestDTO;
import com.example.huellitas_felices.model.Adoption;
import com.example.huellitas_felices.service.AdoptionService;
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
@RequestMapping("/adoptions")
@RequiredArgsConstructor
@Tag(name = "Controlador de adopciones", description = "Gestión de procesos de adopción")
@SecurityRequirement(name = "bearerAuth")
public class AdoptionController {

    private final AdoptionService adoptionService;

    @PostMapping
    @PreAuthorize("hasRole('ADOPTADOR')")
    @Operation(summary = "Solicitar adopción", description = "Permite a un adoptador solicitar la adopción de una mascota")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Solicitud de adopción creada exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Adoption.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error en los datos proporcionados o mascota no disponible",
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
                    description = "Prohibido - Se requiere rol ADOPTADOR",
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
    public ResponseEntity<Adoption> requestAdoption(@Valid @RequestBody AdoptionRequestDTO dto) {
        return ResponseEntity.ok(adoptionService.requestAdoption(dto));
    }

    @PutMapping("/{id}/complete")
    @PreAuthorize("hasRole('EMPLEADO') or hasRole('SUPERADMIN')")
    @Operation(summary = "Confirmar adopción", description = "Permite a un empleado confirmar y completar el proceso de adopción")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Adopción confirmada exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Adoption.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "La adopción no está en estado válido para completar",
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
                    description = "Adopción no encontrada",
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
    public ResponseEntity<Adoption> completeAdoption(
            @Parameter(description = "ID de la adopción a confirmar") @PathVariable Long id) {
        return ResponseEntity.ok(adoptionService.completeAdoption(id));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADOPTADOR') or hasRole('EMPLEADO') or hasRole('SUPERADMIN')")
    @Operation(summary = "Ver adopciones por usuario", description = "Consulta el estado de las adopciones de un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Adoption.class))
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
                    description = "Prohibido - Permisos insuficientes",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
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
    public ResponseEntity<List<Adoption>> getAdoptionsByUserId(
            @Parameter(description = "ID del usuario para consultar sus adopciones") @PathVariable Long userId) {
        return ResponseEntity.ok(adoptionService.getAdoptionsByUserId(userId));
    }

    @GetMapping("/my-adoptions")
    @PreAuthorize("hasRole('ADOPTADOR')")
    @Operation(summary = "Ver mis adopciones", description = "Consulta el estado de las adopciones del usuario autenticado")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Adoption.class))
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
                    description = "Prohibido - Se requiere rol ADOPTADOR",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Adoptador no encontrado",
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
    public ResponseEntity<List<Adoption>> getMyAdoptions() {
        return ResponseEntity.ok(adoptionService.getAdoptionsByCurrentUser());
    }
}