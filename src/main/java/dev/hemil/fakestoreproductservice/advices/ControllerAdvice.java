package dev.hemil.fakestoreproductservice.advices;

import dev.hemil.fakestoreproductservice.dtos.ErrorDto;
import dev.hemil.fakestoreproductservice.exceptions.CategoryNotFoundException;
import dev.hemil.fakestoreproductservice.exceptions.InvalidTokenException;
import dev.hemil.fakestoreproductservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException exception){
        ErrorDto errorDto = new ErrorDto();

        errorDto.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCategoryNotFoundException(CategoryNotFoundException exception){

        ErrorDto errorDto = new ErrorDto();

        errorDto.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorDto> invalidTokenException(InvalidTokenException exception){
        ErrorDto errorDto = new ErrorDto();

        errorDto.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
