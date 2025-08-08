package com.example.huellitas_felices.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AdopterRegisterDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,100}$",
            message = "Nombre inválido")
    private String nombre;

    @NotBlank @Email
    private String correo;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{10,}$",
            message = "Contraseña débil"
    )
    private String password;

    @NotBlank
    @Pattern(regexp = "^\\d{10}$", message = "El teléfono debe ser de 10 dígitos")
    private String telefono;

    @NotBlank
    @Size(min = 10, max = 250, message = "Dirección 10-250 chars")
    private String direccion;
}
