package com.example.huellitas_felices.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PetStatusUpdateDTO {
    @NotNull
    private Long petId;

    private Long adoptadorId; // requerido si el estado pasa a EN_PROCESO_ADOPCION o ADOPTADO

    @NotNull
    private String nuevoEstado; // "DISPONIBLE" | "EN_PROCESO_ADOPCION" | "ADOPTADO"
}
