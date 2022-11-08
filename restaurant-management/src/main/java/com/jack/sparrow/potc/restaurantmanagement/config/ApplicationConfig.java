package com.jack.sparrow.potc.restaurantmanagement.config;

import com.jack.sparrow.potc.restaurantmanagement.model.Cart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfig {

    @Bean("cartMap")
    public Map<String, Cart> createCartMap(){
        return new HashMap<>();
    }
}
