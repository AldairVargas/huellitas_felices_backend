package com.example.huellitas_felices.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserUpdateDTO {

    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,100}$",
            message = "Nombre inválido, solo letras y espacios (2-100)")
    private String nombre;

    @Email(message = "Correo inválido")
    private String correo;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{10,}$",
            message = "Min 10, 1 mayúscula, 1 minúscula, 1 número y 1 símbolo"
    )
    private String password;
}