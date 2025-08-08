package com.example.huellitas_felices.service;

import com.example.huellitas_felices.model.User;
import com.example.huellitas_felices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        User u = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        // Convertimos 'superadmin' -> ROLE_SUPERADMIN, etc.
        String roleName = "ROLE_" + u.getRol().getNombre().toUpperCase();
        return new org.springframework.security.core.userdetails.User(
                u.getCorreo(),
                u.getPassword(),
                List.of(new SimpleGrantedAuthority(roleName))
        );
    }
}
