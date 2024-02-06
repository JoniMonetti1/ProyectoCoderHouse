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

-- Crear la tabla Venta
CREATE TABLE venta (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT,
    fecha DATE,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Crear la tabla de unión para la relación muchos a muchos entre Venta y Producto
CREATE TABLE venta_producto (
    venta_id INT,
    producto_id INT,
    PRIMARY KEY (venta_id, producto_id),
    FOREIGN KEY (venta_id) REFERENCES venta(id),
    FOREIGN KEY (producto_id) REFERENCES producto(id)
);