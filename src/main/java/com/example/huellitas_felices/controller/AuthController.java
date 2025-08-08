package com.example.huellitas_felices.controller;

import com.example.huellitas_felices.dto.*;
import com.example.huellitas_felices.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Registro genérico (si no mandas rol en el DTO, queda como "adoptador")
    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> registerUser(@Valid @RequestBody UserRegisterDTO dto) {
        return ResponseEntity.ok(authService.registerUser(dto));
    }

    // Registro específico de adoptador (crea User + Adopter)
    @PostMapping("/register-adoptador")
    public ResponseEntity<JwtResponseDTO> registerAdopter(@Valid @RequestBody AdopterRegisterDTO dto) {
        return ResponseEntity.ok(authService.registerAdopter(dto));
    }

    // Login → devuelve JWT
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@Valid @RequestBody LoginRequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
