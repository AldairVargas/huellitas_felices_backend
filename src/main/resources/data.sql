INSERT INTO roles (id, nombre) VALUES
                                   (1, 'superadmin'), (2, 'empleado'), (3, 'adoptador')
    ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

INSERT INTO categories (id, nombre) VALUES
                                        (1, 'Perro'), (2, 'Gato'), (3, 'Hámster'), (4, 'Pájaro')
    ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- Mascotas por defecto
INSERT INTO pets (id, nombre, raza, categoria_id, color, peso, estatura, descripcion, fecha_ingreso, estado) VALUES
    (1, 'Max', 'Labrador', 1, 'Dorado', 25.50, 0.65, 'Perro muy cariñoso y juguetón, ideal para familias con niños. Le encanta correr y jugar en el parque.', '2024-01-15', 'DISPONIBLE'),
    (2, 'Luna', 'Husky Siberiano', 1, 'Gris con blanco', 28.00, 0.58, 'Perra enérgica y leal, necesita mucho ejercicio diario. Perfecta para personas activas que disfruten del senderismo.', '2024-01-20', 'DISPONIBLE'),
    (3, 'Milo', 'Persa', 2, 'Naranja', 4.20, 0.25, 'Gato tranquilo y cariñoso, perfecto para departamentos. Le gusta dormir al sol y recibir caricias.', '2024-01-10', 'DISPONIBLE'),
    (4, 'Nala', 'Siamés', 2, 'Crema con puntos oscuros', 3.80, 0.23, 'Gata muy inteligente y vocal, le gusta 'conversar' con sus dueños. Muy sociable y juguetona.', '2024-02-01', 'DISPONIBLE'),
    (5, 'Buddy', 'Golden Retriever', 1, 'Dorado claro', 32.00, 0.70, 'Perro extremadamente amigable y paciente. Excelente con niños, muy obediente y fácil de entrenar.', '2024-01-25', 'DISPONIBLE'),
    (6, 'Whiskers', 'Maine Coon', 2, 'Atigrado gris', 6.50, 0.30, 'Gato grande y gentil, conocido como el gigante gentil de los gatos. Muy sociable y tolerante.', '2024-02-05', 'DISPONIBLE'),
    (7, 'Rocky', 'Bulldog Francés', 1, 'Atigrado', 12.00, 0.35, 'Perro pequeño pero con gran personalidad. Perfecto para espacios pequeños, muy leal y protector.', '2024-01-30', 'DISPONIBLE'),
    (8, 'Coco', 'Angora', 2, 'Blanco puro', 3.50, 0.22, 'Gata elegante de pelo largo, muy limpia y refinada. Le gusta la tranquilidad y los espacios cómodos.', '2024-02-10', 'DISPONIBLE'),
    (9, 'Thor', 'Pastor Alemán', 1, 'Negro con café', 35.00, 0.75, 'Perro inteligente y protector, excelente guardián. Necesita dueños con experiencia y ejercicio regular.', '2024-01-18', 'DISPONIBLE'),
    (10, 'Bella', 'Ragdoll', 2, 'Seal point', 5.20, 0.28, 'Gata extremadamente dócil y relajada, se deja manejar como una muñeca de trapo. Perfecta para niños.', '2024-02-03', 'DISPONIBLE'),
    (11, 'Charlie', 'Beagle', 1, 'Tricolor', 15.50, 0.40, 'Perro cazador pero muy amigable, excelente olfato. Le gusta explorar y es muy sociable con otros perros.', '2024-01-22', 'DISPONIBLE'),
    (12, 'Princess', 'Bengalí', 2, 'Dorado manchado', 4.80, 0.26, 'Gata exótica con patrón de leopardo, muy activa y juguetona. Le gusta trepar y explorar alturas.', '2024-02-07', 'DISPONIBLE'),
    (13, 'Bruno', 'Rottweiler', 1, 'Negro con café', 45.00, 0.68, 'Perro fuerte y leal, excelente protector de familia. Necesita socialización temprana y dueños experimentados.', '2024-01-12', 'EN_PROCESO_ADOPCION'),
    (14, 'Sasha', 'Azul Ruso', 2, 'Gris azulado', 3.20, 0.24, 'Gata tímida pero muy cariñosa una vez que confía. Prefiere ambientes tranquilos y rutinas estables.', '2024-02-12', 'DISPONIBLE'),
    (15, 'Duke', 'San Bernardo', 1, 'Blanco con café', 65.00, 0.85, 'Perro gigante gentil, muy paciente con niños. Necesita espacio grande y cuidados especiales por su tamaño.', '2024-01-08', 'DISPONIBLE')
    ON DUPLICATE KEY UPDATE 
        nombre = VALUES(nombre),
        raza = VALUES(raza),
        categoria_id = VALUES(categoria_id),
        color = VALUES(color),
        peso = VALUES(peso),
        estatura = VALUES(estatura),
        descripcion = VALUES(descripcion),
        fecha_ingreso = VALUES(fecha_ingreso),
        estado = VALUES(estado);
