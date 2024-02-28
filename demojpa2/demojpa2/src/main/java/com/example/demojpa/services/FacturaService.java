package com.example.demojpa.services;

import com.example.demojpa.models.*;
import  com.example.demojpa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository daoFactura;

    @Autowired
    private ProductoRepository daoProducto;

    @Autowired
    private ClienteRepository daoCliente;


    public void cargarDatosFactura() {
        String[] descripciones = {"Play 4", "Play 5", "Play 2"};
        Double[] precios = {1000000.0, 900000.0, 9999999.9};

        Random aleatorioInt = new Random();

        for (int a = 0; a < 3; a++) {
            Producto producto = new Producto(descripciones[a], Integer.toString(aleatorioInt.nextInt(100000000)), 5, precios[a]);
            daoProducto.save(producto);
        }

        for (int i = 1; i <= 3; i++) {
            Long id = (long) i;
            Optional<Cliente> clienteEnBBDD = daoCliente.findById(id);
            if (clienteEnBBDD.isPresent()) {
                Factura comprobante = new Factura(clienteEnBBDD.get(), 0.00);
                List<Producto> listaProductos = new ArrayList<>();
                AtomicInteger cantidadProductos = new AtomicInteger();

                int topeProductos = aleatorioInt.nextInt((5 - 1) + 1) + 1;
                for (int x = 0; x < topeProductos; x++) {
                    Long productoId = (long) (aleatorioInt.nextInt(2) + 1);
                    Optional<Producto> productoEnBBDD = daoProducto.findById(productoId);
                    productoEnBBDD.ifPresent(listaProductos::add);
                }

                List<DetalleFactura> detallesFactura = listaProductos.stream()
                        .map(producto -> {
                            int cantidad = aleatorioInt.nextInt(10) + 1;
                            double precio = producto.getPrice() * cantidad;
                            cantidadProductos.addAndGet(cantidad);

                            DetalleFactura detalleFactura = new DetalleFactura(cantidad, precio, comprobante);
                            detalleFactura.setProducto(producto);
                            return detalleFactura;
                        })
                        .collect(Collectors.toList());

                double total = detallesFactura.stream()
                        .mapToDouble(DetalleFactura::getPrecioProducto)
                        .sum();

                comprobante.setCantidadProductos(cantidadProductos.get());
                comprobante.setTotal(total);
                comprobante.setDetalleFactura(detallesFactura);

                daoFactura.save(comprobante);
            }
        }
    }

    public List<FacturaDTO> listarFacturas() {
        List<FacturaDTO> facturaDTOList = new ArrayList<>();
        for (Factura factura : daoFactura.findAll()) {
            facturaDTOList.add(generarFacturaDTO(factura));
        }
        return facturaDTOList;
    }

    private FacturaDTO generarFacturaDTO(Factura factura) {
        FacturaDTO facturaDTO = new FacturaDTO();
        facturaDTO.setId(factura.getId());

        List<DetalleFacturaDTO> detalleFacturaDTOList = factura.getDetalleFactura().stream()
                .map(detalleFactura -> {
                    DetalleFacturaDTO detalleFacturaDTO = new DetalleFacturaDTO();
                    detalleFacturaDTO.setCantidad(detalleFactura.getCantidad());

                    Producto producto = detalleFactura.getProducto();
                    ProductoDTO productoDTO = new ProductoDTO();
                    productoDTO.setCodigo(producto.getCode());
                    productoDTO.setDescripcion(producto.getDescription());

                    detalleFacturaDTO.setProductoDTO(productoDTO);
                    detalleFacturaDTO.setPrecioProducto(detalleFactura.getPrecioProducto());

                    return detalleFacturaDTO;
                })
                .collect(Collectors.toList());

        facturaDTO.setProductosVendidos(factura.getCantidadProductos());
        facturaDTO.setDetalleFacturaDTO(detalleFacturaDTOList);

        // Suponiendo que la fecha de la factura se ajusta 3 horas antes
        facturaDTO.setFecha(Date.from(factura.getFecha().toInstant().minus(3, ChronoUnit.HOURS)));

        Cliente cliente = factura.getCliente();
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setApellido(cliente.getLastname());
        clienteDTO.setDni(cliente.getDocNumber());
        clienteDTO.setNombre(cliente.getName());

        facturaDTO.setCliente(clienteDTO);
        facturaDTO.setTotal(factura.getTotal());

        return facturaDTO;
    }



    public ResponseEntity<String> agregarFactura(Factura factura) {
        RestTemplate restTemplate = new RestTemplate();
        boolean httpError = false;
        String fechaString = "";
        try {
            Long idCliente = factura.getCliente().getId();
            Optional<Cliente> db = daoCliente.findById(idCliente);
            if (db.isPresent()) {
                Factura facturaAGuardar = new Factura();
                facturaAGuardar.setCliente(db.get());

                try {
                    fechaString = restTemplate.getForObject("http://worldclockapi.com/api/json/utc/now", String.class);
                } catch (HttpStatusCodeException e) {
                    facturaAGuardar.setFecha(new Date());
                    httpError = true;
                }
                if (!httpError) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                    Date fechaD = sf.parse(fechaString);
                    facturaAGuardar.setFecha(Date.from(fechaD.toInstant().minus(3, ChronoUnit.HOURS)));
                }

                facturaAGuardar.setId(factura.getId());
                facturaAGuardar.setTotal(factura.getTotal());
                List<DetalleFactura> lineas = new ArrayList<>();
                Double totalFactura = 0.00;
                int cantidadProductos = 0;
                for (DetalleFactura linea : factura.getDetalleFactura()) {
                    Producto producto;
                    Long idProducto = linea.getProducto().getId();
                    Optional<Producto> productoEnBBDD = daoProducto.findById(idProducto);
                    if (productoEnBBDD.isPresent()) {
                        producto = productoEnBBDD.get();
                        DetalleFactura dfAux = new DetalleFactura(); // Crear una nueva instancia en cada iteración
                        dfAux.setFactura(facturaAGuardar);
                        dfAux.setPrecioProducto(producto.getPrice());
                        totalFactura += producto.getPrice();
                        if (producto.getStock() >= linea.getCantidad()) {
                            dfAux.setCantidad(linea.getCantidad());
                            producto.setStock(producto.getStock() - linea.getCantidad());
                            daoProducto.save(producto);
                            cantidadProductos += linea.getCantidad();
                            dfAux.setProducto(producto);
                            lineas.add(dfAux);
                        } else {
                            return ResponseEntity.status(409).body("cod.409: Cantidad de productos a vender mayor al stock disponible");
                        }
                    } else {
                        return ResponseEntity.status(409).body("cog. 409: No existe Producto con esa id");
                    }
                }
                facturaAGuardar.setTotal(totalFactura);
                facturaAGuardar.setCantidadProductos(cantidadProductos);
                facturaAGuardar.setDetalleFactura(lineas);
                daoFactura.save(facturaAGuardar);
                return ResponseEntity.status(200).body("cod. 200: Operacion exitosa");
            } else {
                return ResponseEntity.status(409).body("cog. 409: No existe Cliente con esa id");
            }
        } catch (Exception e) {
            return ResponseEntity.status(409).body("cog. 409: La operacion no se pudo llevar a cabo");
        }
    }


    private FacturaDTO crearFactura(Factura factura) {
        FacturaDTO dto = new FacturaDTO();
        dto.setId(factura.getId());

        List<DetalleFacturaDTO> dtoDetalle = new ArrayList<>();
        for (DetalleFactura producto : factura.getDetalleFactura()) {
            DetalleFacturaDTO linea = new DetalleFacturaDTO();
            linea.setCantidad(producto.getCantidad());
            ProductoDTO productoDTO = new ProductoDTO();
            productoDTO.setCodigo(producto.getProducto().getCode());
            productoDTO.setDescripcion(producto.getProducto().getDescription());
            linea.setProductoDTO(productoDTO);
            linea.setPrecioProducto(producto.getPrecioProducto());
            dtoDetalle.add(linea);

        }
        dto.setProductosVendidos(factura.getCantidadProductos());
        dto.setDetalleFacturaDTO(dtoDetalle);
        dto.setFecha(Date.from(factura.getFecha().toInstant().minus(3, ChronoUnit.HOURS)));
        dto.setTotal(factura.getTotal());
        ClienteDTO clienteDTO = new ClienteDTO();
        Cliente cliente;
        cliente = factura.getCliente();
        clienteDTO.setApellido(cliente.getLastname());
        clienteDTO.setDni(cliente.getDocNumber());
        clienteDTO.setNombre(cliente.getName());
        dto.setCliente(clienteDTO);
        return dto;
    }


    public ResponseEntity<String> eliminarFactura(Long id) {
        try {
            Optional<Factura> facturaOptional = daoFactura.findById(id);
            if (facturaOptional.isPresent()) {
                daoFactura.deleteById(id);
                return ResponseEntity.status(200).body("cog.200: Factura eliminada exitosamente");
            } else {
                return ResponseEntity.status(409).body("cog.409: No se encontró ninguna factura con ese id");
            }
        } catch (Exception e) {
            return ResponseEntity.status(409).body("cog.409: La operación de eliminación de la factura no pudo realizarse");
        }
    }

}



