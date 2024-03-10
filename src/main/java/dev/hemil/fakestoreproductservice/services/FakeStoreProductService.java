package dev.hemil.fakestoreproductservice.services;

import dev.hemil.fakestoreproductservice.dtos.CreateProductRequestDto;
import dev.hemil.fakestoreproductservice.dtos.FakeStoreProductDto;
import dev.hemil.fakestoreproductservice.exceptions.CategoryNotFoundException;
import dev.hemil.fakestoreproductservice.exceptions.ProductNotFoundException;
import dev.hemil.fakestoreproductservice.models.Category;
import dev.hemil.fakestoreproductservice.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{

    //Bean of RestTemplate to inject into service constructor.
    private RestTemplate restTemplate;

    /*This is just the constructor of this service file, and we are injecting the
    RestTemplate bean into it while calling the service.
     */
    public FakeStoreProductService(RestTemplate restTemplate){

        this.restTemplate = restTemplate;
    }

    /* These are the methods that have been implemented from ProductService interface
    */
    @Override
    public Product getSingleProduct(long productId) throws ProductNotFoundException {
         ResponseEntity<FakeStoreProductDto> fakeStoreProductResponse = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/" + productId,
                FakeStoreProductDto.class
        );

         FakeStoreProductDto fakeStoreSingleProduct = fakeStoreProductResponse.getBody();

         if(fakeStoreSingleProduct == null){
             throw new ProductNotFoundException("Product with id : "+productId+ " doesn't exist. Retry with some other product.");
         }
        return fakeStoreSingleProduct.toProduct();
    }


    @Override
    public Product createProduct(String title,
                                 String description,
                                 String category,
                                 double price,
                                 String image) {

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setDescription(description);
        fakeStoreProductDto.setCategory(category);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setImage(image);

        ResponseEntity<FakeStoreProductDto> createFakeStoreProductResponse = restTemplate.postForEntity(
                "https://fakestoreapi.com/products",
                fakeStoreProductDto,
                FakeStoreProductDto.class
        );

        FakeStoreProductDto response = createFakeStoreProductResponse.getBody();

        return response.toProduct();
    }

    @Override
    public List<Product> getProductsInSpecificCategory(String category) throws CategoryNotFoundException {
         ResponseEntity<FakeStoreProductDto[]> fakeStoreProductsResponse = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/category/" + category,
                FakeStoreProductDto[].class
        );

         FakeStoreProductDto[] productsInSpecificCategory = fakeStoreProductsResponse.getBody();

        if(productsInSpecificCategory.length == 0){
             throw new CategoryNotFoundException("Category "+category+" does not exist. Retry with some mentioned categories");
         }

         List<Product> allProductsInACategory = new ArrayList<>();

        for(FakeStoreProductDto dto : productsInSpecificCategory){
            allProductsInACategory.add(dto.toProduct());
        }
        return allProductsInACategory;
    }

    @Override
    public List<Product> allProductsInStore() throws ProductNotFoundException {
        ResponseEntity<FakeStoreProductDto[]> fakeStoreAllProductResponse = restTemplate.getForEntity(
                "https://fakestoreapi.com/products" ,
                FakeStoreProductDto[].class
        );

        FakeStoreProductDto[] allProductsReceived = fakeStoreAllProductResponse.getBody();

        if(allProductsReceived.length == 0){
            throw new ProductNotFoundException("Store doesn't have products to be  displayed");
        }

        List<Product>  allProducts = new ArrayList<>();
        for(FakeStoreProductDto dto : allProductsReceived){
            allProducts.add(dto.toProduct());
        }
        return allProducts;
    }

    @Override
    public List<String> getAllProductsCategories() {
        List<String> categories = restTemplate.getForObject(
                "https://fakestoreapi.com/products/categories",
                List.class
        );
        return categories;
    }

    @Override
    public Product updateProduct(long id,
                                 String title,
                                 String description,
                                 String category,
                                 double price,
                                 String image) throws ProductNotFoundException {

        getSingleProduct(id);

        FakeStoreProductDto updateRequest = new FakeStoreProductDto();
        updateRequest.setId(id);
        updateRequest.setTitle(title);
        updateRequest.setDescription(description);
        updateRequest.setCategory(category);
        updateRequest.setPrice(price);
        updateRequest.setImage(image);

         restTemplate.put(
                "https://fakestoreapi.com/products/" + id,
                updateRequest,
                FakeStoreProductDto.class/* <- here i am facing error related to method*/
        );
        return updateRequest.toProduct();
    }

    @Override
    public void deleteProduct(long id) throws ProductNotFoundException {
        getSingleProduct(id);

        restTemplate.delete(
                "https://fakestoreapi.com/products/"+id);
    }


}