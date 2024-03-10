package dev.hemil.fakestoreproductservice.controllers;

import dev.hemil.fakestoreproductservice.dtos.CreateProductRequestDto;
import dev.hemil.fakestoreproductservice.exceptions.CategoryNotFoundException;
import dev.hemil.fakestoreproductservice.exceptions.ProductNotFoundException;
import dev.hemil.fakestoreproductservice.models.Product;
import dev.hemil.fakestoreproductservice.services.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){

        this.productService = productService;
    }
    /* Get all products
    Get all categories
    Update a product
    Delete a product
    Get products in a specific category
    */

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequestDto request) {

        Product createProductResponse = productService.createProduct(
                request.getTitle(),
                request.getDescription(),
                request.getCategory(),
                request.getPrice(),
                request.getImage()
        );

        return new ResponseEntity<>(createProductResponse, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductDetails(@PathVariable("id") long productId) throws ProductNotFoundException {

        Product singleProduct = productService.getSingleProduct(productId);

        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() throws ProductNotFoundException {

        List<Product> allProducts =productService.allProductsInStore();

        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/products/category/{productCategory}")
    public ResponseEntity<List<Product>> getProductsInSpecificCategory(@PathVariable("productCategory") String category) throws CategoryNotFoundException, ProductNotFoundException {

        List<Product> productsInSpecificCategory = productService.getProductsInSpecificCategory(category);

        return new ResponseEntity<>(productsInSpecificCategory, HttpStatus.OK);
    }

    @GetMapping("/products/categories")
    public ResponseEntity<List<String>> getAllCategories(){

        List<String> allCategories =productService.getAllProductsCategories();

        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id,@RequestBody CreateProductRequestDto updateRequest) throws ProductNotFoundException {

        Product updateProductResponse = productService.updateProduct(id,
                updateRequest.getTitle(),
                updateRequest.getDescription(),
                updateRequest.getCategory(),
                updateRequest.getPrice(),
                updateRequest.getImage()
        );

        return new ResponseEntity<>(updateProductResponse, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long productId) throws ProductNotFoundException {

        productService.deleteProduct(productId);
        String deleteResponse = "Product with id : " + productId + " is deleted";
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }

    //    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDto> handleProductNotFoundExeption(ProductNotFoundException exception) {
//
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage(exception.getMessage());
//
//        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
////        return null;
//    }

    // Limited to only the exceptions thrown from this controller
    // Controller Advices: Global

    // if this controller ever ends up throwing ProductNotFoundException.class
    // for any reason, don't throw that exception as is.
    // Instead call this method and return what this method is returning
}
