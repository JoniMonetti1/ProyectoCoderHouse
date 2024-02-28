CREATE DATABASE coderprueba1;

USE coderprueba1;

-- Crear la tabla Cliente
CREATE TABLE cliente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    lastname VARCHAR(255),
    docNumber VARCHAR(255)
);

-- Crear la tabla Producto
CREATE TABLE producto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255),
    code VARCHAR(255),
    stock INT,
    price DOUBLE
);

-- Crear la tabla facturas
CREATE TABLE facturas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT,
    fecha DATETIME,
    total DOUBLE,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE detalleFactura (
    id INT AUTO_INCREMENT,
    id_factura INT,
    cantidad INT NOT NULL,
    id_producto INT,
    precio DOUBLE,
    FOREIGN KEY(id_factura) REFERENCE facturas(id), FOREIGN KEY(id_producto) REFERENCES prouductos(id), PRIMARY KEY(id)
);