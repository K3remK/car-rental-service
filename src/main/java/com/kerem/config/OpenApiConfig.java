package com.kerem.config;

import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Schema for APIError<String>
        Schema<Object> standardErrorSchema = new Schema<>();
        standardErrorSchema.addProperty("id", new io.swagger.v3.oas.models.media.StringSchema());
        standardErrorSchema.addProperty("errorTime", new io.swagger.v3.oas.models.media.DateTimeSchema());
        standardErrorSchema.addProperty("error", new io.swagger.v3.oas.models.media.StringSchema());

        // Schema for APIError<Map<String, List<String>>>
        Schema<Object> validationErrorSchema = new Schema<>();
        validationErrorSchema.addProperty("id", new io.swagger.v3.oas.models.media.StringSchema());
        validationErrorSchema.addProperty("errorTime", new io.swagger.v3.oas.models.media.DateTimeSchema());
        validationErrorSchema.addProperty("error", new io.swagger.v3.oas.models.media.MapSchema()
                .additionalProperties(new io.swagger.v3.oas.models.media.ArraySchema()
                        .items(new io.swagger.v3.oas.models.media.StringSchema())));

        return new OpenAPI()
                .info(new Info()
                        .title("Car Rental Service API")
                        .version("1.0")
                        .description("API documentation for the Car Rental Service application"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSchemas("StandardError", standardErrorSchema)
                        .addSchemas("ValidationError", validationErrorSchema));
    }
}
