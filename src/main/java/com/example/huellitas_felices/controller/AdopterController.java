package com.example.huellitas_felices.controller;

import com.example.huellitas_felices.model.Adopter;
import com.example.huellitas_felices.service.AdopterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adopters")
@RequiredArgsConstructor
@Tag(name = "Controlador de adoptadores", description = "Gestión de adoptadores del sistema")
@SecurityRequirement(name = "bearerAuth")
public class AdopterController {

    private final AdopterService adopterService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Obtener todos los adoptadores", description = "Lista todos los adoptadores registrados en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Adopter.class))
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
                    description = "Prohibido - Se requiere rol ADMIN o EMPLOYEE",
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
    public ResponseEntity<List<Adopter>> getAllAdopters() {
        return ResponseEntity.ok(adopterService.getAllAdopters());
    }

    @GetMapping("/by-email/{correo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @Operation(summary = "Obtener adoptador por correo", description = "Busca un adoptador por su correo electrónico")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Adoptador encontrado exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Adopter.class))
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
                    responseCode = "401",
                    description = "No autorizado - Token inválido",
                    content = {
                            @Content(mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Prohibido - Se requiere rol ADMIN o EMPLOYEE",
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
    public ResponseEntity<Adopter> getAdopterByEmail(@PathVariable String correo) {
        Optional<Adopter> adopter = adopterService.findByEmail(correo);
        
        if (adopter.isPresent()) {
            return ResponseEntity.ok(adopter.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}