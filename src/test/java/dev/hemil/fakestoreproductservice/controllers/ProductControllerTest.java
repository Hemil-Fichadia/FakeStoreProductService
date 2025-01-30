package dev.hemil.fakestoreproductservice.controllers;

import dev.hemil.fakestoreproductservice.exceptions.InvalidTokenException;
import dev.hemil.fakestoreproductservice.exceptions.ProductNotFoundException;
import dev.hemil.fakestoreproductservice.models.Product;
import dev.hemil.fakestoreproductservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    //This is the bean of actual controller, not the mocked one
    @Autowired
    private ProductController productController;
    /* For each of the controllers or services or whatsoever to be tested should be
    having its own file for test. It will be in a separate package of its own, like
    controller in controllers package and service in services package and itelliJ gives
    this option by clicking Alt+insert on name of the respective class to create a test
    file in its respective folder.
    While writing test cases, we will consider all three possiblities for each of the API
    or methods of services, and they are positive, negative and edge cases

    1) Positive test case :- where test cases are generated keeping in mind to be passed
    by the system which we are testing.

    2) Negative test case :- These test cases are generated to be rejected by the system
    that we are testing

    3) Edge cases/ corner cases :- These are only found if one is aware of what, why and
    how a system is behaving, according to the output, and then try to generate a test case.
    */
    //All the tests related to product controller will be in this file

    /* Here in this controller we are testing if the controller works as expected or
    not so we don't actually need to involve other services in this testing, instead
    we will mock the response that we are supposed to get if everything is working
    accordingly, so instead of calling an actual service, we are testing the behaviour
    of the exact functionality.
    */

    /*This is the mocked bean of the service as we don't need the actual object.
    The annotation @MockBean allows us to create a mock of an actual object.
    */
    @MockBean
    @Qualifier("fakeStoreProductService")
    private ProductService productService;
    @Test
    void createProduct() {
    }

    @Test
    void getProductDetails() throws ProductNotFoundException, InvalidTokenException {
        Product product = new Product();
        product.setTitle("Lenovo legion pro 5i 2024");
        product.setPrice(176000);
        product.setDescription("Best productivity and creator laptop");

        when(productService.getSingleProduct(10L))
                .thenReturn(product);

        assertEquals(product, productController.getProductDetails(10L, "token"));
    }

    @Test
    void getAllProducts() throws ProductNotFoundException {
        //Call the mocked ProductService and get a list of Products
        Product p1 =  new Product();
        p1.setTitle("iPhone 15");
        p1.setImageUrl("iPhone-img");
        p1.setDescription("2023 iPhone");

        Product p2 = new Product();
        p2.setTitle("One plus  7");
        p2.setImageUrl("oneplus-img");
        p2.setDescription("2019 oneplus 7");

        Product p3 = new Product();
        p3.setTitle("One plus 12 pro");
        p2.setImageUrl("oneplus12pro-img");
        p3.setDescription("2024 One plus 12 pro");

        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p2);
        products.add(p3);
        /* If this method is called, then call the mocked ProductService object
        and return the following thing in .thenReturn
        */
        when(
                productService.allProductsInStore()
        ).thenReturn(
                products
        );
        //The Actual response
        /* As productController's object is not mocked, so the response
        which we get is actual not mocked.
        */
        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();
        List<Product> response = responseEntity.getBody();

        //assertEquals(products.size()+3, response.size());
        assertEquals(products, response);
    }

    @Test
    void getProductsInSpecificCategory() {
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void replaceProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void getProductsByPage() {
    }

    @Test
    void getProductsByPageSortedByPrice() {
    }
}