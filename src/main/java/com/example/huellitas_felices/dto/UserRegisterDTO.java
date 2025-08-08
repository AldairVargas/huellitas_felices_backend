package com.example.huellitas_felices.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,100}$",
            message = "Nombre inválido, solo letras y espacios (2-100)")
    private String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo inválido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{10,}$",
            message = "Min 10, 1 mayúscula, 1 minúscula, 1 número y 1 símbolo"
    )
    private String password;

    // Opcional: rol a asignar por superadmin (si no viene, por defecto 'adoptador')
    private String rol;
}
