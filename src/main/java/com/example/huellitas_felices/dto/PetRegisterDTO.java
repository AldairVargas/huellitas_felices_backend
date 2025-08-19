package com.example.huellitas_felices.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PetRegisterDTO {
        @NotBlank
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,100}$", message = "Nombre inválido")
        private String nombre;

        @NotBlank
        @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,100}$", message = "Raza inválida")
        private String raza;

        @NotNull
        private Long categoriaId;

        @NotBlank
        @Size(min = 3, max = 40)
        private String color;

        @NotNull
        @DecimalMin(value = "0.1", message = "Peso > 0")
        @DecimalMax(value = "150.0", message = "Peso irreal")
        private BigDecimal peso;

        @NotNull
        @DecimalMin(value = "0.1", message = "Estatura > 0")
        @DecimalMax(value = "2.5", message = "Estatura irreal")
        private BigDecimal estatura;

        @NotBlank
        @Size(min = 15, max = 350)
        private String descripcion;

        @Size(max = 500)
        private String img; // URL o ruta de la imagen de la mascota (opcional)
}
