package dev.hemil.fakestoreproductservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class FakeStoreProductServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(FakeStoreProductServiceApplication.class, args);
    }

}
