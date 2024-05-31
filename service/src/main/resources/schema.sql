CREATE TABLE IF NOT EXISTS `articulo` (
    `articulo_id` int AUTO_INCREMENT  PRIMARY KEY,
    `nombre` varchar(100) NOT NULL,
    `descripcion` varchar(10) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL
    );