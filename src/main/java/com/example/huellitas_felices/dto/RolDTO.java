package com.example.huellitas_felices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RolDTO {
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(min = 2, max = 20, message = "El nombre del rol debe tener entre 2 y 20 caracteres")
    private String nombre;
}