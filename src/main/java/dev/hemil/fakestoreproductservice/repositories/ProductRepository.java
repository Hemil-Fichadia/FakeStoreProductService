package dev.hemil.fakestoreproductservice.repositories;

import dev.hemil.fakestoreproductservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product p);
    //The attributes that DB automatically generates will not be
    //in the params but will be there in the returned object


    @Override
    List<Product> findAll();

    Product findByIdIs(Long id);

    List<Product> findAllByCategory_Title(String category);

    void deleteById(Long id);

    @Override
    boolean existsById(Long id);
}
