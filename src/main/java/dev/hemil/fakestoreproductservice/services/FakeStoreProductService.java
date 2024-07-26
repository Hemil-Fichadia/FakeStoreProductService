package dev.hemil.fakestoreproductservice.services;


import dev.hemil.fakestoreproductservice.dtos.FakeStoreProductDto;
import dev.hemil.fakestoreproductservice.exceptions.CategoryNotFoundException;
import dev.hemil.fakestoreproductservice.exceptions.ProductNotFoundException;
import dev.hemil.fakestoreproductservice.models.Category;
import dev.hemil.fakestoreproductservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
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
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
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
    public Product replaceProduct(Long id,
                                 String title,
                                 String description,
                                 String category,
                                 double price,
                                 String image) throws ProductNotFoundException {

        /* The get single product will handle the exception to id not existing.
        * */
        Product existingProduct = getSingleProduct(id);

        FakeStoreProductDto replaceRequest = new FakeStoreProductDto();
        replaceRequest.setId(id);
        replaceRequest.setTitle(title);
        replaceRequest.setDescription(description);
        replaceRequest.setCategory(category);
        replaceRequest.setPrice(price);
        replaceRequest.setImage(image);

         restTemplate.put(
                "https://fakestoreapi.com/products/" + id,
                 replaceRequest,
                FakeStoreProductDto.class/* <- here i am facing error related to method*/
        );

         /*updating and returning the existing product to ensure that the changes are
         carried out on some exiting product.
         */
         existingProduct.setTitle(replaceRequest.getTitle());
         existingProduct.setDescription(replaceRequest.getDescription());
         existingProduct.setPrice(replaceRequest.getPrice());
         existingProduct.setImageUrl(replaceRequest.getImage());
         /* Even if the category sent is same as previous, replace it with the one
         that is received to ensure all changes are carried out.
         * */
         Category newCategory = new Category();
         newCategory.setTitle(replaceRequest.getCategory());

        return existingProduct;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, String category, double price, String image) throws ProductNotFoundException {
        /* Get single product will handle the exception of product not found.
        * */
        Product existingProduct = getSingleProduct(id);

        FakeStoreProductDto updateRequest = new FakeStoreProductDto();
        /* Transferring all fields data of existing product to updateReqeust and then
        updating those fields which are not null
        * */
        updateRequest.setTitle(existingProduct.getTitle());
        updateRequest.setDescription(existingProduct.getDescription());
        updateRequest.setPrice(existingProduct.getPrice());
        updateRequest.setImage(existingProduct.getImageUrl());
        updateRequest.setCategory(existingProduct.getCategory().getTitle());

        if(title != null){
            updateRequest.setTitle(title);
            existingProduct.setTitle(title);
        }
        if(description != null){
            updateRequest.setDescription(description);
            existingProduct.setDescription(description);
        }
        if(price != 0.0d){
            updateRequest.setPrice(price);
            existingProduct.setPrice(price);
        }
        if(image != null){
            updateRequest.setImage(image);
            existingProduct.setImageUrl(image);
        }
        if(category != null){
            updateRequest.setCategory(category);
            Category newCategory = new Category();
            newCategory.setTitle(category);
            existingProduct.setCategory(newCategory);
        }
        //This patch request is faulty in fakestoreapi
//        restTemplate.patchForObject(
//                "https://fakestoreapi.com/products" + id,
//                updateRequest,
//                FakeStoreProductDto.class
//        );

        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        getSingleProduct(id);

        restTemplate.delete(
                "https://fakestoreapi.com/products/"+id);
    }

    @Override
    public Page<Product> getAllProductsByPage(Integer pageSize, Integer pageNumber, String sort) {

        return null;
    }


}