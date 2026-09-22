package com.uniyeisi.backend.service;

import com.uniyeisi.backend.entity.Producto;
import com.uniyeisi.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    // Guardar un producto
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Buscar un producto por ID
    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Eliminar un producto
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}