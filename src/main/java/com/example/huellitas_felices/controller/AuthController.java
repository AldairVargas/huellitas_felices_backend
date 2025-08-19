package com.example.huellitas_felices.controller;

import com.example.huellitas_felices.dto.*;
import com.example.huellitas_felices.service.AuthService;
import org.springframework.security.core.Authentication;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Controlador de autenticación", description = "Operaciones de login y registro de usuarios")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login de usuario", description = "Autenticación de cualquier tipo de usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login exitoso",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Credenciales incorrectas",
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
    public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody LoginRequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register/adopter")
    @Operation(summary = "Registro de adoptador", description = "Registro de un nuevo adoptador en el sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro exitoso",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDTO.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error en los datos de registro",
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
    public ResponseEntity<JwtResponseDTO> registerAdopter(@Valid @RequestBody AdopterRegisterDTO dto) {
        return ResponseEntity.ok(authService.registerAdopter(dto));
    }

    @GetMapping("/profile")
    @Operation(summary = "Obtener perfil del usuario", description = "Obtiene la información del usuario autenticado actualmente")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil obtenido exitosamente",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDTO.class))
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
    public ResponseEntity<UserProfileDTO> getCurrentUserProfile(Authentication authentication) {
        return ResponseEntity.ok(authService.getCurrentUserProfile(authentication.getName()));
    }
}
