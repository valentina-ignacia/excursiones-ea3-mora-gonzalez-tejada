-- =====================================================================
-- Ruta Austral Excursiones - Base de datos (MySQL 8)
-- Crea la base de datos excursiones, sus tablas y los datos iniciales.
-- Ejecuta el script completo. No lo modifiques.
-- =====================================================================

DROP DATABASE IF EXISTS excursiones;
CREATE DATABASE excursiones CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci;
USE excursiones;

-- Catálogo de tipos
CREATE TABLE tipo_excursion (
    id_tipo      INT          NOT NULL,
    descripcion  VARCHAR(30)  NOT NULL,
    CONSTRAINT pk_tipo_excursion PRIMARY KEY (id_tipo),
    CONSTRAINT uq_tipo_excursion_descripcion UNIQUE (descripcion)
);

-- Registros.
-- desnivel_metros, guia_bilingue, seguro_incluido se usan solo para Trekking;
-- duracion_horas se usa solo para Kayak.
CREATE TABLE excursion (
    id                INT           NOT NULL AUTO_INCREMENT,
    id_tipo           INT           NOT NULL,
    codigo            VARCHAR(80)   NOT NULL,
    participantes     INT           NOT NULL,
    distancia_km      DECIMAL(10,2) NOT NULL,
    desnivel_metros   DECIMAL(10,2) NULL,
    guia_bilingue     BOOLEAN       NULL,
    seguro_incluido   BOOLEAN       NULL,
    duracion_horas    DECIMAL(10,2) NULL,
    CONSTRAINT pk_excursion PRIMARY KEY (id),
    CONSTRAINT fk_excursion_tipo FOREIGN KEY (id_tipo) REFERENCES tipo_excursion (id_tipo),
    CONSTRAINT ck_excursion_participantes CHECK (participantes BETWEEN 1 AND 30),
    CONSTRAINT ck_excursion_distancia_km CHECK (distancia_km > 0),
    CONSTRAINT ck_excursion_desnivel_metros CHECK (desnivel_metros > 0),
    CONSTRAINT ck_excursion_duracion_horas CHECK (duracion_horas > 0)
);

-- Datos iniciales
INSERT INTO tipo_excursion (id_tipo, descripcion) VALUES (1, 'Trekking');
INSERT INTO tipo_excursion (id_tipo, descripcion) VALUES (2, 'Kayak');
