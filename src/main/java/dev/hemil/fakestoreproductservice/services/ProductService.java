package dev.hemil.fakestoreproductservice.services;

import dev.hemil.fakestoreproductservice.dtos.CreateProductRequestDto;
import dev.hemil.fakestoreproductservice.models.Category;
import dev.hemil.fakestoreproductservice.models.Product;

import java.util.List;

public interface ProductService {
    /*This is a service interface which is implemented and
    all the methods are declared here first and then override
    in the implemented class
    * */
    Product getSingleProduct(long productId);

    Product createProduct(String title,
                          String description,
                          String category,
                          double price,
                          String image);

    List<Product> getProductsInSpecificCategory(String category);

    List<Product> allProductsInStore();

    List<String> getAllProductsCategories();

    Product updateProduct(long id,
                          String title,
                          String description,
                          String category,
                          double price,
                          String image);

    void deleteProduct(long id);
}
