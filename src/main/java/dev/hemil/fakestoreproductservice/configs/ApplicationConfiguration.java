package dev.hemil.fakestoreproductservice.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

    @LoadBalanced
    @Bean
    public RestTemplate createRestTemplate(){
        return new RestTemplate();
    }
}
