INSERT INTO roles (id, nombre) VALUES
                                   (1, 'superadmin'), (2, 'empleado'), (3, 'adoptador')
    ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

INSERT INTO categories (id, nombre) VALUES
                                        (1, 'Perro'), (2, 'Gato'), (3, 'Hámster'), (4, 'Pájaro')
    ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);
