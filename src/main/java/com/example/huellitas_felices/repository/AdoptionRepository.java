package com.example.huellitas_felices.repository;

import com.example.huellitas_felices.model.Adoption;
import com.example.huellitas_felices.model.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    List<Adoption> findByAdopter(Adopter adopter);
    List<Adoption> findByAdopterId(Long adopterId);
}