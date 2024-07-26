package dev.hemil.fakestoreproductservice.repositories.projections;

public interface ProductProjection {

    Long getId();
    String getTitle();
    String getDescription();
    double getPrice();
    String getImageUrl();
    String getCategory();
}
