package com.example.huellitas_felices.service;

import com.example.huellitas_felices.dto.UserRegisterDTO;
import com.example.huellitas_felices.model.Rol;
import com.example.huellitas_felices.model.User;
import com.example.huellitas_felices.repository.RolRepository;
import com.example.huellitas_felices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        User u = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        String roleName = "ROLE_" + u.getRol().getNombre().toUpperCase();
        return new org.springframework.security.core.userdetails.User(
                u.getCorreo(),
                u.getPassword(),
                List.of(new SimpleGrantedAuthority(roleName))
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createEmployee(UserRegisterDTO dto) {
        if (userRepository.existsByCorreo(dto.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }

        String rolNombre = (dto.getRol() == null || dto.getRol().isBlank()) 
                ? "empleado" 
                : dto.getRol().toLowerCase();

        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolNombre));

        User user = User.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol(rol)
                .build();
        
        return userRepository.save(user);
    }
}
