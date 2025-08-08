package com.example.huellitas_felices.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdoptionRequestDTO {
    @NotNull
    private Long petId;

    @NotNull
    private Long adoptadorId; // el adoptador que inicia el proceso
}
