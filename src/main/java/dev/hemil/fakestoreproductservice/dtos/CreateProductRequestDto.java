package dev.hemil.fakestoreproductservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDto {
    private String title;
    private String image;
    private String category;
    private String description;
    private double price;
}
