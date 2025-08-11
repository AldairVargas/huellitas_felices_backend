package com.example.huellitas_felices.service;

import com.example.huellitas_felices.dto.AdoptionRequestDTO;
import com.example.huellitas_felices.model.*;
import com.example.huellitas_felices.repository.AdopterRepository;
import com.example.huellitas_felices.repository.AdoptionRepository;
import com.example.huellitas_felices.repository.PetRepository;
import com.example.huellitas_felices.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final AdopterRepository adopterRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Transactional
    public Adoption requestAdoption(AdoptionRequestDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        
        User user = userRepository.findByCorreo(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
        Adopter adopter = adopterRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Adoptador no encontrado"));

        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        if (pet.getEstado() != PetStatus.DISPONIBLE) {
            throw new RuntimeException("La mascota no está disponible para adopción");
        }

        Adoption adoption = Adoption.builder()
                .adopter(adopter)
                .pet(pet)
                .fechaSolicitud(LocalDate.now())
                .motivoAdopcion(dto.getMotivoAdopcion())
                .estado(AdoptionStatus.SOLICITADA)
                .build();

        pet.setEstado(PetStatus.EN_PROCESO_ADOPCION);
        petRepository.save(pet);

        return adoptionRepository.save(adoption);
    }

    @Transactional
    public Adoption completeAdoption(Long adoptionId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        
        User empleado = userRepository.findByCorreo(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Adoption adoption = adoptionRepository.findById(adoptionId)
                .orElseThrow(() -> new RuntimeException("Adopción no encontrada"));

        if (adoption.getEstado() != AdoptionStatus.SOLICITADA) {
            throw new RuntimeException("La adopción no está en estado válido para completar");
        }

        adoption.setEstado(AdoptionStatus.COMPLETADA);
        adoption.setFechaCompletada(LocalDate.now());
        adoption.setEmpleadoConfirmador(empleado);

        Pet pet = adoption.getPet();
        pet.setEstado(PetStatus.ADOPTADO);
        pet.setAdoptador(adoption.getAdopter());
        pet.setFechaAdopcion(LocalDate.now());
        petRepository.save(pet);

        return adoptionRepository.save(adoption);
    }

    public List<Adoption> getAdoptionsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
        Adopter adopter = adopterRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Adoptador no encontrado"));

        return adoptionRepository.findByAdopter(adopter);
    }

    public List<Adoption> getAdoptionsByCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        
        User user = userRepository.findByCorreo(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
        Adopter adopter = adopterRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Adoptador no encontrado"));

        return adoptionRepository.findByAdopter(adopter);
    }
}