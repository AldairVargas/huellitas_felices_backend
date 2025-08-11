package com.example.huellitas_felices.service;

import com.example.huellitas_felices.dto.RolDTO;
import com.example.huellitas_felices.model.Rol;
import com.example.huellitas_felices.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    public Rol getRolById(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }

    @Transactional
    public Rol createRol(RolDTO dto) {
        if (rolRepository.findByNombre(dto.getNombre().toLowerCase()).isPresent()) {
            throw new RuntimeException("Ya existe un rol con el nombre: " + dto.getNombre());
        }

        Rol rol = Rol.builder()
                .nombre(dto.getNombre().toLowerCase())
                .build();
        
        return rolRepository.save(rol);
    }

    @Transactional
    public Rol updateRol(Long id, RolDTO dto) {
        Rol rol = getRolById(id);
        
        // Verificar si el nuevo nombre ya existe en otro rol
        rolRepository.findByNombre(dto.getNombre().toLowerCase())
                .ifPresent(existingRol -> {
                    if (!existingRol.getId().equals(id)) {
                        throw new RuntimeException("Ya existe un rol con el nombre: " + dto.getNombre());
                    }
                });
        
        rol.setNombre(dto.getNombre().toLowerCase());
        return rolRepository.save(rol);
    }

    @Transactional
    public void deleteRol(Long id) {
        Rol rol = getRolById(id);
        
        // Verificar que no sea uno de los roles básicos del sistema
        String nombreRol = rol.getNombre().toLowerCase();
        if ("superadmin".equals(nombreRol) || "empleado".equals(nombreRol) || "adoptador".equals(nombreRol)) {
            throw new RuntimeException("No se puede eliminar un rol básico del sistema: " + nombreRol);
        }
        
        rolRepository.deleteById(id);
    }
}