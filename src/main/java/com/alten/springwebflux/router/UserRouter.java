package com.alten.springwebflux.router;

import com.alten.springwebflux.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class UserRouter {
    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunctionUser() {
        return RouterFunctions.route()
                .GET("/router/getAllUsers", userHandler::getAllUsers)
                .GET("/router/getUserById/{id}", userHandler::getUserById)
                .POST("/router/createUser", userHandler::createUser)
                .PUT("/router/updateUser/{id}", userHandler::updateUser)
                .DELETE("/router/deleteUser/{id}", userHandler::deleteUser)
                .build();
    }
}
