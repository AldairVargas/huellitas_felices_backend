package com.example.huellitas_felices.repository;

import com.example.huellitas_felices.model.Adopter;
import com.example.huellitas_felices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdopterRepository extends JpaRepository<Adopter, Long> {
    Optional<Adopter> findByUserId(Long userId);
    Optional<Adopter> findByUser(User user);
}
