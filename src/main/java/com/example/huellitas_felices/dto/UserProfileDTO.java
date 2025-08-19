package com.example.huellitas_felices.dto;

import com.example.huellitas_felices.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;
    private Rol rol;
    private LocalDateTime fechaRegistro;
}