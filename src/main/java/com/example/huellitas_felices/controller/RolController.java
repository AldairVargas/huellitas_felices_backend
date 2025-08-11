package com.example.huellitas_felices.controller;

import com.example.huellitas_felices.dto.RolDTO;
import com.example.huellitas_felices.model.Rol;
import com.example.huellitas_felices.service.RolService;
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
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Controlador de roles", description = "Gestión de roles del sistema (solo superadmin)")
@SecurityRequirement(name = "bearerAuth")
public class RolController {

    private final RolService rolService;

    @GetMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    @Operation(summary = "Obtener todos los roles", description = "Lista todos los roles disponibles en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista obtenida exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))
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
    public ResponseEntity<List<Rol>> getAllRoles() {
        return ResponseEntity.ok(rolService.getAllRoles());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    @Operation(summary = "Obtener rol por ID", description = "Consulta un rol específico por su identificador")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rol encontrado exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))
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
                    responseCode = "404",
                    description = "Rol no encontrado",
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
    public ResponseEntity<Rol> getRolById(
            @Parameter(description = "ID del rol a consultar") @PathVariable Long id) {
        return ResponseEntity.ok(rolService.getRolById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    @Operation(summary = "Crear nuevo rol", description = "Registra un nuevo rol en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rol creado exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error en los datos proporcionados o rol ya existe",
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
    public ResponseEntity<Rol> createRol(@Valid @RequestBody RolDTO dto) {
        return ResponseEntity.ok(rolService.createRol(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    @Operation(summary = "Actualizar rol", description = "Actualiza la información de un rol existente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rol actualizado exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error en los datos proporcionados o nombre ya existe",
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
                    responseCode = "404",
                    description = "Rol no encontrado",
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
    public ResponseEntity<Rol> updateRol(
            @Parameter(description = "ID del rol a actualizar") @PathVariable Long id, 
            @Valid @RequestBody RolDTO dto) {
        return ResponseEntity.ok(rolService.updateRol(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol del sistema (no se pueden eliminar roles básicos)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Rol eliminado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No se puede eliminar un rol básico del sistema",
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
                    responseCode = "404",
                    description = "Rol no encontrado",
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
    public ResponseEntity<Void> deleteRol(
            @Parameter(description = "ID del rol a eliminar") @PathVariable Long id) {
        rolService.deleteRol(id);
        return ResponseEntity.ok().build();
    }
}