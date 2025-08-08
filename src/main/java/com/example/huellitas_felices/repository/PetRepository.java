package com.example.huellitas_felices.repository;

import com.example.huellitas_felices.model.Pet;
import com.example.huellitas_felices.model.PetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByEstado(PetStatus estado);
    List<Pet> findByCategoriaNombre(String categoria);
}
