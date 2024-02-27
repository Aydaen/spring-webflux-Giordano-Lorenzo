package com.alten.springwebflux.router;

import com.alten.springwebflux.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class UserRouter {
    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunctionUser() {
        return route()
                .path("/users", builder -> builder
                        .GET("", userHandler::getAllUsers)
                        .GET("/{id}", userHandler::getUserById)
                        .POST("", userHandler::createUser)
                        .PUT("/{id}", userHandler::updateUser)
                        .DELETE("/{id}", userHandler::deleteUser))
                .build();
    }
}
