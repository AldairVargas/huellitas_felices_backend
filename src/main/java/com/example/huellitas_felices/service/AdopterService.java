package com.example.huellitas_felices.service;

import com.example.huellitas_felices.model.Adopter;
import com.example.huellitas_felices.repository.AdopterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdopterService {

    private final AdopterRepository adopterRepository;

    public Optional<Adopter> findByEmail(String correo) {
        return adopterRepository.findByUserCorreo(correo);
    }

    public List<Adopter> getAllAdopters() {
        return adopterRepository.findAll();
    }
}