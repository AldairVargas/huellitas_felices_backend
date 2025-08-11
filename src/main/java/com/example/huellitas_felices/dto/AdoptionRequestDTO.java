package com.example.huellitas_felices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdoptionRequestDTO {
    @NotNull
    private Long petId;

    @NotBlank
    @Size(min = 10, max = 500)
    private String motivoAdopcion;
}
