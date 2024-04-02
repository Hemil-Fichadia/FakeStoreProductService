package dev.hemil.fakestoreproductservice.services;

import dev.hemil.fakestoreproductservice.dtos.CreateProductRequestDto;
import dev.hemil.fakestoreproductservice.exceptions.CategoryNotFoundException;
import dev.hemil.fakestoreproductservice.exceptions.ProductNotFoundException;
import dev.hemil.fakestoreproductservice.models.Category;
import dev.hemil.fakestoreproductservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    /*This is a service interface that is implemented, and
    all the methods are declared here first and then override
    in the implemented class
    * */
    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    Product createProduct(String title,
                          String description,
                          String category,
                          double price,
                          String image);

    List<Product> getProductsInSpecificCategory(String category) throws ProductNotFoundException, CategoryNotFoundException;

    List<Product> allProductsInStore() throws ProductNotFoundException;

    List<String> getAllProductsCategories();

    Product replaceProduct(Long id,
                          String title,
                          String description,
                          String category,
                          double price,
                          String image) throws ProductNotFoundException;
    Product updateProduct(Long id,
                          String title,
                          String description,
                          String category,
                          double price,
                          String image) throws ProductNotFoundException;

    void deleteProduct(Long id) throws ProductNotFoundException;

    Page<Product> getAllProductsByPage(Integer pageSize, Integer pageNumber, String sort);
}
