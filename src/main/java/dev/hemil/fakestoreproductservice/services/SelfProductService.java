package dev.hemil.fakestoreproductservice.services;

import dev.hemil.fakestoreproductservice.exceptions.CategoryNotFoundException;
import dev.hemil.fakestoreproductservice.exceptions.ProductNotFoundException;
import dev.hemil.fakestoreproductservice.models.Category;
import dev.hemil.fakestoreproductservice.models.Product;
import dev.hemil.fakestoreproductservice.repositories.CategoryRepository;
import dev.hemil.fakestoreproductservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findByIdIs(productId);
    }

    @Override
    public Product createProduct(String title, String description, String category, double price, String image) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);

        Category existingCategory = categoryRepository.findByTitle(category);

        if(existingCategory == null){
            existingCategory = new Category();
            existingCategory.setTitle(category);
        }

        product.setCategory(existingCategory);

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    @Override
    public List<Product> getProductsInSpecificCategory(String category) throws CategoryNotFoundException {
        Category existingCategory = categoryRepository.findByTitle(category);
        if(existingCategory == null){
            throw new CategoryNotFoundException("The category "+category+" does not exist try with some other category");
        }
        return productRepository.findAllByCategory_Title(category);
    }

    @Override
    public List<Product> allProductsInStore() throws ProductNotFoundException {
        return productRepository.findAll();
    }

    @Override
    public List<String> getAllProductsCategories() {
        List<Category> allCategoriesFromDatabase = categoryRepository.findAll();
        List<String> allCategories = new ArrayList<>();
        for(Category category : allCategoriesFromDatabase){
            allCategories.add(category.getTitle());
        }
        return allCategories;
    }

    @Override
    public Product replaceProduct(Long id, String title, String description, String category, double price, String image) throws ProductNotFoundException {
        Product existingProduct = getSingleProduct(id);
        existingProduct.setTitle(title);
        existingProduct.setDescription(description);
        existingProduct.setPrice(price);
        existingProduct.setImageUrl(image);
        Category newCategory = new Category();
        newCategory.setTitle(category);
        existingProduct.setCategory(newCategory);

        Product updatedProduct = productRepository.save(existingProduct);
        return updatedProduct;
    }

    @Override
    public Product updateProduct(Long id, String title, String description, String category, double price, String image) throws ProductNotFoundException {
        Product existingProduct = getSingleProduct(id);

        if(title != null){
            existingProduct.setTitle(title);
        }
        if(description != null){
            existingProduct.setDescription(description);
        }
        if(category != null){
            Category newCategory = new Category();
            newCategory.setTitle(category);
        }
        if(price != 0.0d){
            existingProduct.setPrice(price);
        }
        if(image != null){
            existingProduct.setImageUrl(image);
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return updatedProduct;
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException("Product with id "+id+" does not exist. Try with some other product");
        }
        productRepository.deleteById(id);
    }
    
    /* This is pagination of the data that we receive as response to make sure that we don't
    make more clutter on UI so we display the products in terms of pages which makes it look
    more interactive instead of all products at one go.
    In this sort field, we pass the actual column name of a database on basis of which
    we are looking forward to display data in sorted manner.
     */
    @Override
    public Page<Product> getAllProductsByPage(Integer pageSize, Integer pageNumber, String sort) {
        Pageable pageable = null;
        if(sort != null){
            //Pagination with sorting
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, sort);
        }
        else{
            //Pagination without sorting
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return productRepository.findAll(pageable);
    }
}
