package dev.hemil.fakestoreproductservice.controllers;

import dev.hemil.fakestoreproductservice.commons.AuthenticationCommons;
import dev.hemil.fakestoreproductservice.dtos.CreateProductRequestDto;
import dev.hemil.fakestoreproductservice.dtos.ReplaceProductRequestDto;
import dev.hemil.fakestoreproductservice.dtos.UpdateProductRequestDto;
import dev.hemil.fakestoreproductservice.dtos.UserDto;
import dev.hemil.fakestoreproductservice.exceptions.CategoryNotFoundException;
import dev.hemil.fakestoreproductservice.exceptions.InvalidTokenException;
import dev.hemil.fakestoreproductservice.exceptions.ProductNotFoundException;
import dev.hemil.fakestoreproductservice.models.Product;
import dev.hemil.fakestoreproductservice.services.ProductService;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;
    private AuthenticationCommons authenticationCommons;
    private RestTemplate restTemplate;

    public ProductController(@Qualifier("selfProductService") ProductService productService,
                             AuthenticationCommons authenticationCommons,
                             RestTemplate restTemplate){

        this.productService = productService;
        this.authenticationCommons = authenticationCommons;
        this.restTemplate = restTemplate;
    }
    /* Get all products
    Get all categories
    Update a product
    Delete a product
    Get products in a specific category
    */

//    @CachePut(value = "product", key = "#")
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

    @GetMapping("/products/{id}/{token}")
    public ResponseEntity<Product> getProductDetails(@PathVariable("id") Long productId, @PathVariable("token") String token) throws ProductNotFoundException, InvalidTokenException {

//        UserDto userDto = authenticationCommons.validateToken(token);
//        if(userDto == null){
//            throw new InvalidTokenException("Session expired, kindly re-login");
//        }
//
//        Product singleProduct = productService.getSingleProduct(productId);
//
//        return new ResponseEntity<>(singleProduct, HttpStatus.OK);

        restTemplate.getForObject("http://userservice/users/1", UserDto.class);

        return new ResponseEntity<>(productService.getSingleProduct(productId), HttpStatus.OK);
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() throws ProductNotFoundException {

        List<Product> allProducts = productService.allProductsInStore();

        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/products/category/{productCategory}")
    public ResponseEntity<List<Product>> getProductsInSpecificCategory(@PathVariable("productCategory") String category) throws CategoryNotFoundException, ProductNotFoundException {

        List<Product> productsInSpecificCategory = productService.getProductsInSpecificCategory(category);

        return new ResponseEntity<>(productsInSpecificCategory, HttpStatus.OK);
    }

    @GetMapping("/products/categories")
    public ResponseEntity<List<String>> getAllCategories(){

        List<String> allCategories = productService.getAllProductsCategories();

        return new ResponseEntity<>(allCategories, HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id, @RequestBody ReplaceProductRequestDto replaceRequest) throws ProductNotFoundException {

        Product replaceProductResponse = productService.replaceProduct(id,
                replaceRequest.getTitle(),
                replaceRequest.getDescription(),
                replaceRequest.getCategory(),
                replaceRequest.getPrice(),
                replaceRequest.getImage()
        );

        return new ResponseEntity<>(replaceProductResponse, HttpStatus.OK);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody UpdateProductRequestDto updateRequest) throws ProductNotFoundException {
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
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {

        productService.deleteProduct(productId);
        String deleteResponse = "Product with id : " + productId + " is deleted";
        return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
    }

    @GetMapping("/products/paginate/{pageSize}/{pageNumber}")
    public ResponseEntity getProductsByPage(@PathVariable("pageSize") int pageSize, @PathVariable("pageNumber") int pageNumber){
        Page<Product> productPage = productService.getAllProductsByPage(pageSize, pageNumber, null);
        return ResponseEntity.ok(productPage.getContent());
    }

    @GetMapping("productsByPrice/paginate/{pageSize}/{pageNumber}")
    public ResponseEntity getProductsByPageSortedByPrice(@PathVariable("pageSize") int pageSize, @PathVariable("pageNumber") int pageNumber){
        Page<Product> productPage = productService.getAllProductsByPage(pageSize, pageNumber, "price");
        return ResponseEntity.ok(productPage.getContent());
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
