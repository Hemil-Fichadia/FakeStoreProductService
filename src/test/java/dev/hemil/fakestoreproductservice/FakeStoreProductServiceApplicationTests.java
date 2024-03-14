package dev.hemil.fakestoreproductservice;

import dev.hemil.fakestoreproductservice.repositories.CategoryRepository;
import dev.hemil.fakestoreproductservice.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FakeStoreProductServiceApplicationTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testingQueries() {
//        productRepository.findAll();
        productRepository.findAllByCategory_Title("kitchen");
    }
}
