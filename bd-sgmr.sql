CREATE DATABASE sgmr_db;

CREATE TABLE sgmr_db.administradores(
    administrador_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    cuit BIGINT NOT NULL,
    direccion VARCHAR(100),
    email VARCHAR(100),
    telefono BIGINT,
    activo BOOLEAN DEFAULT 1,
    fecha_creacion DATE NOT NULL
);

CREATE TABLE sgmr_db.clientes(
    cliente_id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    cu BIGINT NOT NULL,
    tipo_persona ENUM('juridica', 'fisica'),
    direccion VARCHAR(100),
    email VARCHAR(100),
    telefono BIGINT,
    activo BOOLEAN DEFAULT 1,
    fecha_creacion DATE NOT NULL,
    administrador_id INT NOT NULL,
    FOREIGN KEY (administrador_id) REFERENCES administradores(administrador_id)
);

CREATE TABLE sgmr_db.certificados(
    certificado_id INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(cliente_id),
    fecha_tratamiento DATE NOT NULL,
    fecha_vencimiento DATE NOT NULL
);

INSERT INTO sgmr_db.administradores (nombre, cuit, direccion, email, telefono, activo, fecha_creacion) VALUES
('Juan Pérez', 20304050607, 'Av. Libertador 1234', 'juan.perez@mail.com', 1156789123, TRUE, '2024-01-10'),
('María Gómez', 27384930201, 'Calle Falsa 456', 'maria.gomez@mail.com', 1145678921, TRUE, '2024-02-05'),
('Carlos Fernández', 20223344550, 'Av. Córdoba 789', 'carlos.fernandez@mail.com', 1167891234, TRUE, '2024-03-12'),
('Lucía Martínez', 30201928374, 'Boulevard San Juan 321', 'lucia.martinez@mail.com', 1134567890, TRUE, '2024-04-08'),
('Diego Ríos', 27384930202, 'Pasaje Lima 987', 'diego.rios@mail.com', 1176543210, TRUE, '2024-05-20');

INSERT INTO sgmr_db.clientes (nombre, cu, tipo_persona, direccion, email, telefono, activo, fecha_creacion, administrador_id) VALUES
('Empresa Alfa', 30567891234, 'juridica', 'Av. Siempre Viva 742', 'contacto@empresaalfa.com', 1143216789, TRUE, '2024-01-15', 1),
('Pedro Sánchez', 20304560789, 'fisica', 'Calle Luna 567', 'pedro.sanchez@mail.com', 1156789432, TRUE, '2024-02-10', 2),
('Consultora Beta', 30765432109, 'juridica', 'Av. Rivadavia 123', 'info@consultorabeta.com', 1167894321, TRUE, '2024-03-20', 3),
('Ana López', 20309876543, 'fisica', 'Pasaje Córdoba 456', 'ana.lopez@mail.com', 1134567123, TRUE, '2024-04-15', 4),
('Industrias Gamma', 30987654321, 'juridica', 'Boulevard San Martín 890', 'ventas@industriasgamma.com', 1176543987, TRUE, '2024-05-25', 5);

INSERT INTO sgmr_db.certificados (cliente_id, fecha_tratamiento, fecha_vencimiento) VALUES
(1, '2024-01-20', '2025-01-20'),
(2, '2024-02-15', '2025-02-15'),
(3, '2024-03-25', '2025-03-25'),
(4, '2024-04-20', '2025-04-20'),
(5, '2024-05-30', '2025-05-30');
