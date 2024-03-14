package dev.hemil.fakestoreproductservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplaceProductRequestDto {

    private String title;
    private String image;
    private String description;
    private String category;
    private double price;

}
