package com.example.huellitas_felices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 40)
    private String nombre;
}
