package com.uniyeisi.backend;

import com.uniyeisi.backend.entity.Producto;
import com.uniyeisi.backend.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    public DataLoader(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (productoRepository.count() == 0) {

            productoRepository.save(new Producto(
                    null,
                    "Camisa Diario",
                    "M",
                    55000.0,
                    "Normal Superior de Medellín",
                    "camisa-normal.jpg"
            ));

            productoRepository.save(new Producto(
                    null,
                    "Sudadera",
                    "L",
                    95000.0,
                    "Normal Superior de Medellín",
                    "sudadera-normal.jpg"
            ));

            productoRepository.save(new Producto(
                    null,
                    "Camisa Diario",
                    "S",
                    55000.0,
                    "Juan de Dios Carvajal",
                    "camisa-carvajal.jpg"
            ));

            productoRepository.save(new Producto(
                    null,
                    "Sudadera",
                    "M",
                    90000.0,
                    "Juan de Dios Carvajal",
                    "sudadera-carvajal.jpg"
            ));

            System.out.println("==================================");
            System.out.println(" Productos de prueba cargados");
            System.out.println("==================================");
        }

    }
}