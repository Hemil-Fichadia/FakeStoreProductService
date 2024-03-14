package dev.hemil.fakestoreproductservice.dtos;

import dev.hemil.fakestoreproductservice.models.Category;
import dev.hemil.fakestoreproductservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequestDto {

    private String title;
    private String image;
    private String description;
    private String category;
    private double price;

}
