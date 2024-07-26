package dev.hemil.fakestoreproductservice.repositories;

import dev.hemil.fakestoreproductservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByTitle(String title);

    @Override
    List<Category> findAll();

//    boolean findByTitleExists(String category);
}
