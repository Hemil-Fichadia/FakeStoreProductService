package dev.hemil.fakestoreproductservice.dtos;

import dev.hemil.fakestoreproductservice.models.Category;
import dev.hemil.fakestoreproductservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {

    private Long id;
    private String title;
    private String image;
    private String description;
    private String category;
    private double price;

    /*In models, the category is an object but the fakeStoreApi response is
    a string, so we have to take care of it separately and due to that I have created
    a method that converts a fakestoreapi response String category data to on model
    based object and for that we have to include that method inside this DTO.
    * */


    /*This is the category object that our model is having and here I am setting that
        fakestore object response to our model category by making whole separate method that
        sets category into the model object, and then it is set inside the main product object
        of this method.
        * */
    public Product toProduct(){
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);


        Category productCategory = new Category();
        productCategory.setTitle(category);

        product.setCategory(productCategory);

        return product;
    }

    public Category toCategory(){
        Category category = new Category();
        category.setTitle(title);
        return category;
    }
}
