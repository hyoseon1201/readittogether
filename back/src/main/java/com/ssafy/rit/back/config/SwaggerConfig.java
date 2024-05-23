package com.ssafy.rit.back.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

    @OpenAPIDefinition(
            info = @Info(title = "리딧투게더 api",
                    description = "리딧투게더 api",
                    version = "v1"
            )
    )
    @Configuration
    public class SwaggerConfig {

        @Bean
        public GroupedOpenApi memberApi() {
            return GroupedOpenApi.builder()
                    .group("member-api")
                    .pathsToMatch("/members/**")
                    .build();
        }

        @Bean
        public GroupedOpenApi cardApi() {
            return GroupedOpenApi.builder()
                    .group("card-api")
                    .pathsToMatch("/card/**")
                    .build();
        }

        @Bean
        public GroupedOpenApi guestBookApi() {
            return GroupedOpenApi.builder()
                    .group("guestbook-api")
                    .pathsToMatch("/guestbook/**")
                    .build();
        }

        @Bean
        public GroupedOpenApi loginApi() {
            return GroupedOpenApi.builder()
                    .group("login-api")
                    .pathsToMatch("/login")
                    .build();
        }

        @Bean
        public GroupedOpenApi booksAPI() {
            return GroupedOpenApi.builder()
                    .group("books-api")
                    .pathsToMatch("/books/**")
                    .build();
        }

        @Bean
        public GroupedOpenApi bookshelfAPI() {
            return GroupedOpenApi.builder()
                    .group("bookshelf-api")
                    .pathsToMatch("/bookshelf/**")
                    .build();
        }

        @Bean
        public GroupedOpenApi libraryAPI() {
            return GroupedOpenApi.builder()
                    .group("library-api")
                    .pathsToMatch("/library/**")
                    .build();
        }

        @Bean
        public GroupedOpenApi searchAPI() {
            return GroupedOpenApi.builder()
                    .group("search-api")
                    .pathsToMatch("/search/**")
                    .build();
        }

        @Bean
        public GroupedOpenApi postBoxAPI() {
            return GroupedOpenApi.builder()
                    .group("postbox-api")
                    .pathsToMatch("/postbox/**")
                    .build();
        }

        @Bean
        public GroupedOpenApi recommendAPI() {
            return GroupedOpenApi.builder()
                    .group("recommend-api")
                    .pathsToMatch("/recommends/**")
                    .build();
        }

        @Bean
        public OpenAPI openAPI(){
            SecurityScheme securityScheme = new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER).name("Authorization");
            SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

            return new OpenAPI()
                    .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                    .security(Arrays.asList(securityRequirement));
        }

    }
