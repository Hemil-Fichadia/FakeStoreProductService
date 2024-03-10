package dev.hemil.fakestoreproductservice.exceptions;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(String message){
        super(message);
    }
}
