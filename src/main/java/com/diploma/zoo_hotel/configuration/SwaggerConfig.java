package com.diploma.zoo_hotel.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("ZooHotel")
                        .description("Сайт по передержке животных")
                        .contact(new Contact()
                                .name("Измайлова Гулизар-Ханум Юнус-кызы"))
                        .version("0.1.0"));
    }

}
