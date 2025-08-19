package com.example.huellitas_felices.service;

import com.example.huellitas_felices.dto.*;
import com.example.huellitas_felices.model.Adopter;
import com.example.huellitas_felices.model.Rol;
import com.example.huellitas_felices.model.User;
import com.example.huellitas_felices.repository.AdopterRepository;
import com.example.huellitas_felices.repository.RolRepository;
import com.example.huellitas_felices.repository.UserRepository;
import com.example.huellitas_felices.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final AdopterRepository adopterRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public JwtResponseDTO registerUser(UserRegisterDTO dto) {
        if (userRepository.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }
        // rol opcional en DTO; si no viene, por defecto "adoptador"
        String rolNombre = (dto.getRol() == null || dto.getRol().isBlank())
                ? "adoptador"
                : dto.getRol().toLowerCase();

        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));

        User user = User.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol(rol)
                .build();
        userRepository.save(user);

        var claims = new HashMap<String, Object>();
        claims.put("rol", rol.getNombre());
        String token = jwtUtil.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getCorreo(), user.getPassword(),
                        java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + rol.getNombre().toUpperCase()))
                ),
                claims
        );
        return new JwtResponseDTO(token, rol.getNombre(), user.getNombre());
    }

    @Transactional
    public JwtResponseDTO registerAdopter(AdopterRegisterDTO dto) {
        if (userRepository.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }
        Rol rol = rolRepository.findByNombre("adoptador")
                .orElseThrow(() -> new RuntimeException("Rol 'adoptador' no encontrado (revisa data.sql)"));

        User user = User.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol(rol)
                .build();
        userRepository.save(user);

        Adopter adopter = Adopter.builder()
                .user(user)
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .build();
        adopterRepository.save(adopter);

        var claims = new HashMap<String, Object>();
        claims.put("rol", rol.getNombre());
        String token = jwtUtil.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getCorreo(), user.getPassword(),
                        java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + rol.getNombre().toUpperCase()))
                ),
                claims
        );
        return new JwtResponseDTO(token, rol.getNombre(), user.getNombre());
    }

    public JwtResponseDTO login(LoginRequest req) {
        User user = userRepository.findByCorreo(req.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        var claims = new HashMap<String, Object>();
        claims.put("rol", user.getRol().getNombre());
        String token = jwtUtil.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getCorreo(), user.getPassword(),
                        java.util.List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + user.getRol().getNombre().toUpperCase()))
                ),
                claims
        );
        return new JwtResponseDTO(token, user.getRol().getNombre(), user.getNombre());
    }

    public UserProfileDTO getCurrentUserProfile(String correo) {
        User user = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si es adoptador para obtener información adicional
        Optional<Adopter> adopter = adopterRepository.findByUserCorreo(correo);
        
        UserProfileDTO.UserProfileDTOBuilder profileBuilder = UserProfileDTO.builder()
                .id(user.getId())
                .nombre(user.getNombre())
                .correo(user.getCorreo())
                .rol(user.getRol());

        // Si es adoptador, agregar telefono y direccion
        if (adopter.isPresent()) {
            profileBuilder
                    .telefono(adopter.get().getTelefono())
                    .direccion(adopter.get().getDireccion());
        }

        return profileBuilder.build();
    }
}
