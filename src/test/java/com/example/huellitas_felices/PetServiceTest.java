package com.example.huellitas_felices;

import com.example.huellitas_felices.dto.PetRegisterDTO;
import com.example.huellitas_felices.model.Category;
import com.example.huellitas_felices.model.Pet;
import com.example.huellitas_felices.model.PetStatus;
import com.example.huellitas_felices.repository.CategoryRepository;
import com.example.huellitas_felices.repository.PetRepository;
import com.example.huellitas_felices.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PetServiceTest {

    @Autowired
    private PetService petService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testRegisterPetWithImage() {
        // Buscar una categoría existente o crear una nueva
        Category savedCategory = categoryRepository.findByNombre("Perro")
                .orElseGet(() -> {
                    Category categoria = new Category();
                    categoria.setNombre("Perro");
                    return categoryRepository.save(categoria);
                });

        // Crear DTO con imagen
        PetRegisterDTO dto = new PetRegisterDTO();
        dto.setNombre("Luna");
        dto.setRaza("Golden Retriever");
        dto.setCategoriaId(savedCategory.getId());
        dto.setColor("Dorado");
        dto.setPeso(new BigDecimal("25.5"));
        dto.setEstatura(new BigDecimal("0.55"));
        dto.setDescripcion("Luna es una perrita muy cariñosa y juguetona que ama los niños");
        dto.setImg("https://ejemplo.com/imagenes/luna.jpg");

        // Registrar la mascota
        Pet savedPet = petService.registerPet(dto);

        // Verificar que se guardó correctamente
        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());
        assertEquals("Luna", savedPet.getNombre());
        assertEquals("Golden Retriever", savedPet.getRaza());
        assertEquals("https://ejemplo.com/imagenes/luna.jpg", savedPet.getImg());
        assertEquals(PetStatus.DISPONIBLE, savedPet.getEstado());

        // Verificar que se puede recuperar de la base de datos
        Optional<Pet> retrievedPet = petRepository.findById(savedPet.getId());
        assertTrue(retrievedPet.isPresent());
        assertEquals("https://ejemplo.com/imagenes/luna.jpg", retrievedPet.get().getImg());
    }

    @Test
    void testRegisterPetWithoutImage() {
        // Buscar una categoría existente o crear una nueva
        Category savedCategory = categoryRepository.findByNombre("Gato")
                .orElseGet(() -> {
                    Category categoria = new Category();
                    categoria.setNombre("Gato");
                    return categoryRepository.save(categoria);
                });

        // Crear DTO sin imagen
        PetRegisterDTO dto = new PetRegisterDTO();
        dto.setNombre("Mittens");
        dto.setRaza("Siamés");
        dto.setCategoriaId(savedCategory.getId());
        dto.setColor("Blanco y negro");
        dto.setPeso(new BigDecimal("4.2"));
        dto.setEstatura(new BigDecimal("0.25"));
        dto.setDescripcion("Mittens es un gato tranquilo y cariñoso que busca un hogar");
        // No establecer img

        // Registrar la mascota
        Pet savedPet = petService.registerPet(dto);

        // Verificar que se guardó correctamente
        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());
        assertEquals("Mittens", savedPet.getNombre());
        assertNull(savedPet.getImg()); // La imagen debe ser null
        assertEquals(PetStatus.DISPONIBLE, savedPet.getEstado());
    }
}
