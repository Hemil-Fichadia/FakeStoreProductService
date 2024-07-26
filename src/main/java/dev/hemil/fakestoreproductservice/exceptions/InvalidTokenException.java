package dev.hemil.fakestoreproductservice.exceptions;

public class InvalidTokenException extends Exception{
    public InvalidTokenException(String message){
        super(message);
    }
}
