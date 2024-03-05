package dev.hemil.fakestoreproductservice.controllers;

import dev.hemil.fakestoreproductservice.dtos.CreateProductRequestDto;
import dev.hemil.fakestoreproductservice.models.Category;
import dev.hemil.fakestoreproductservice.models.Product;
import dev.hemil.fakestoreproductservice.services.ProductService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){

        this.productService = productService;
    }
    /*just simple modification
    * */
    /* Get all products
    Get all categories
    Update a product
    Delete a product
    Get products in a specific category
    */

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDto request) {
        System.out.println("Controller : "+request.getTitle());
        return productService.createProduct(
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                request.getPrice(),
                request.getImage()
        );
    }

    @GetMapping("/products/{id}")
    public Product getProductDetails(@PathVariable("id") long productId) {
        return productService.getSingleProduct(productId);
    }


    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.allProductsInStore();
    }

    @GetMapping("/products/category/{productCategory}")
    public List<Product> getProductsInSpecificCategory(@PathVariable("productCategory") String category){
        return productService.getProductsInSpecificCategory(category);
    }

    @GetMapping("/products/categories")
    public List<String> getAllCategories(){
        return productService.getAllProductsCategories();
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable("id") long id,@RequestBody CreateProductRequestDto updateRequest) {
        return productService.updateProduct(id,
                updateRequest.getTitle(),
                updateRequest.getDescription(),
                updateRequest.getCategory(),
                updateRequest.getPrice(),
                updateRequest.getImage()
        );
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") long productId){
        productService.deleteProduct(productId);
        /* Added this commit to check the functionality
        * */
    }
}
