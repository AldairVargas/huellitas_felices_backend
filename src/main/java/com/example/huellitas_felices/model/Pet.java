package com.example.huellitas_felices.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String raza;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Category categoria;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal peso; // kg

    @Column(nullable = false, precision = 3, scale = 2)
    private BigDecimal estatura; // m

    @Column(nullable = false, length = 350)
    private String descripcion;

    @Column(length = 500)
    private String img; // URL o ruta de la imagen de la mascota

    @Column(nullable = false)
    private LocalDate fechaIngreso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PetStatus estado;

    @ManyToOne
    @JoinColumn(name = "adoptador_id")
    private Adopter adoptador;

    private LocalDate fechaAdopcion;
}
