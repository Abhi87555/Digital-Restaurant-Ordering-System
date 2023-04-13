package com.jack.sparrow.potc.restaurantmanagement.config;

import com.jack.sparrow.potc.restaurantmanagement.model.Cart;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfig {

    @Bean("cartMap")
    public Map<String, Cart> createCartMap(){
        return new HashMap<>();
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info()
                .title("Restaurant Application 1.0")
                .description("Restaurant Management Application"));
    }
}
