package dev.hemil.fakestoreproductservice.commons;

import dev.hemil.fakestoreproductservice.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationCommons {
    private RestTemplate restTemplate;

    public AuthenticationCommons(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public UserDto validateToken(String token){
        //Make a call to UserService to validate api

        UserDto userDto = restTemplate.getForObject(
                "http://localhost:8181/users/validate/" + token,
                UserDto.class
        );
        return userDto;
    }
}
