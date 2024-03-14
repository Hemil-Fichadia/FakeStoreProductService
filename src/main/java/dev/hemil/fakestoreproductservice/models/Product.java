package dev.hemil.fakestoreproductservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseModel {

    private String title;
    private String description;
    private double price;
    private String imageUrl;
    /*Types of cascades
    1) CascadeType.PERSIST
    Makes sure that category exists before saving a product,
    So if someone tries to save a product whose category does not
    exist in a database, then first save the category present in
    category table and then save product.

    2) CascadeType.REMOVE
    This will make sure that if a product is deleted, then delete
    the category associated with it.
    This is a something
    illogical idea to be executed.

    3)
    * */
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Category category;
}
