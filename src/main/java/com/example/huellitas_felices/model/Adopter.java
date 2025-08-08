package com.example.huellitas_felices.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "adopters")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Adopter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, length = 10)
    private String telefono;

    @Length(min = 10, max = 250)
    @Column(nullable = false)
    private String direccion;
}
