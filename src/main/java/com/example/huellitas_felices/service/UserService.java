package com.example.huellitas_felices.service;

import com.example.huellitas_felices.dto.UserRegisterDTO;
import com.example.huellitas_felices.dto.UserUpdateDTO;
import com.example.huellitas_felices.model.Adopter;
import com.example.huellitas_felices.model.Rol;
import com.example.huellitas_felices.model.User;
import com.example.huellitas_felices.repository.AdopterRepository;
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
    private final AdopterRepository adopterRepository;

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

    public User updateUser(Long userId, UserUpdateDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar campos básicos del usuario
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            user.setNombre(dto.getNombre());
        }

        if (dto.getCorreo() != null && !dto.getCorreo().isBlank()) {
            if (!user.getCorreo().equals(dto.getCorreo()) && userRepository.existsByCorreo(dto.getCorreo())) {
                throw new RuntimeException("Ya existe un usuario con ese correo");
            }
            user.setCorreo(dto.getCorreo());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // Guardar cambios en User
        return userRepository.save(user);
    }

    public void deleteEmployee(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que sea empleado (no adoptador)
        if (adopterRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("No se puede eliminar un adoptador desde este endpoint");
        }

        // Verificar que no sea superadmin
        if ("superadmin".equalsIgnoreCase(user.getRol().getNombre())) {
            throw new RuntimeException("No se puede eliminar un superadmin");
        }

        userRepository.delete(user);
    }
}
