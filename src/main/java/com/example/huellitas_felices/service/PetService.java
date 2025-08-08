package com.example.huellitas_felices.service;

import com.example.huellitas_felices.dto.PetRegisterDTO;
import com.example.huellitas_felices.dto.PetStatusUpdateDTO;
import com.example.huellitas_felices.model.*;
import com.example.huellitas_felices.repository.AdopterRepository;
import com.example.huellitas_felices.repository.CategoryRepository;
import com.example.huellitas_felices.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;
    private final AdopterRepository adopterRepository;

    @Transactional
    public Pet registerPet(PetRegisterDTO dto) {
        Category categoria = categoryRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Pet pet = Pet.builder()
                .nombre(dto.getNombre())
                .raza(dto.getRaza())
                .categoria(categoria)
                .color(dto.getColor())
                .peso(dto.getPeso())
                .estatura(dto.getEstatura())
                .descripcion(dto.getDescripcion())
                .fechaIngreso(LocalDate.now())
                .estado(PetStatus.DISPONIBLE)
                .build();

        return petRepository.save(pet);
    }

    public List<Pet> getAvailablePets() {
        return petRepository.findByEstado(PetStatus.DISPONIBLE);
    }

    @Transactional
    public Pet updatePetStatus(PetStatusUpdateDTO dto) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        PetStatus nuevoEstado = PetStatus.valueOf(dto.getNuevoEstado());

        if (nuevoEstado == PetStatus.EN_PROCESO_ADOPCION || nuevoEstado == PetStatus.ADOPTADO) {
            Adopter adoptador = adopterRepository.findById(dto.getAdoptadorId())
                    .orElseThrow(() -> new RuntimeException("Adoptador no encontrado"));
            pet.setAdoptador(adoptador);
            if (nuevoEstado == PetStatus.ADOPTADO) {
                pet.setFechaAdopcion(LocalDate.now());
            }
        } else {
            pet.setAdoptador(null);
            pet.setFechaAdopcion(null);
        }

        pet.setEstado(nuevoEstado);
        return petRepository.save(pet);
    }
}
