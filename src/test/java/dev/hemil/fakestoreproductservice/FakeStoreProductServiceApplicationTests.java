package dev.hemil.fakestoreproductservice;

import dev.hemil.fakestoreproductservice.repositories.CategoryRepository;
import dev.hemil.fakestoreproductservice.repositories.ProductRepository;
import dev.hemil.fakestoreproductservice.repositories.projections.ProductProjection;
import dev.hemil.fakestoreproductservice.repositories.projections.ProductWithIdAndTitle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
//        productRepository.findAllByCategory_Title("kitchen");
//        List<ProductWithIdAndTitle> receivedProducts = productRepository.getTitlesOfProductsOfGivenCategory(
//                2L
//        );
//        System.out.println(receivedProducts.get(0).getId());
//        System.out.println(receivedProducts.get(0).getTitle());

        /* This projection technique is beneficial in long term as we want to
        make our application more dynamic and so we want to create a single superset
        of interface that can used in scenario of fields are received and so I implemented
        all the methods and then just received some of the fields data and then printed for
        the testing purpose.
        * */
//        List<ProductProjection> receivedProductDetail =
//                productRepository.getProductDetailsOfGivenId(3L);
//        System.out.println(receivedProductDetail.get(0).getId());
//        System.out.println(receivedProductDetail.get(0).getDescription());
//        System.out.println(receivedProductDetail.get(0).getTitle());

        List<ProductProjection> receivedProductData = productRepository.getProductsWithName("OnePlus 12");
        System.out.println("Id : "+receivedProductData.get(0).getId());
        System.out.println("Title : "+receivedProductData.get(0).getTitle());
        System.out.println("Price : "+receivedProductData.get(0).getPrice());
        System.out.println("Description : "+receivedProductData.get(0).getDescription());
    }


}
