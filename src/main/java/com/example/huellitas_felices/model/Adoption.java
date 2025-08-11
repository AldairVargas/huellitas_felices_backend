package com.example.huellitas_felices.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "adoptions")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Adoption {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "adopter_id", nullable = false)
    private Adopter adopter;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    @Column(nullable = false, length = 500)
    private String motivoAdopcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdoptionStatus estado;

    private LocalDate fechaCompletada;

    @ManyToOne
    @JoinColumn(name = "empleado_confirmador_id")
    private User empleadoConfirmador;
}