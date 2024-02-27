package com.alten.springwebflux.handler;

import com.alten.springwebflux.dto.BookingDTO;
import com.alten.springwebflux.dto.UserDTO;
import com.alten.springwebflux.model.User;
import com.alten.springwebflux.service.impl.DefaultUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@Component
@RequiredArgsConstructor
public class UserHandler {
    private final DefaultUserService userService;
    private final ModelMapper modelMapper;

    Mono<ServerResponse> notFoundResponse = ServerResponse.notFound().build();
    Mono<ServerResponse> badRequestResponse = ServerResponse.badRequest().build();

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        Flux<User> userFlux = userService.getAll()
                .map(userDTO -> modelMapper.map(userDTO, User.class));

        return ServerResponse
                .status(HttpStatus.OK)
                .body(userFlux, User.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        String userId = request.pathVariable("id");

        Mono<UserDTO> userDTOMono = userService.getById(userId);

        return userDTOMono.flatMap(userDTO ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .contentType(APPLICATION_JSON)
                                .body(userDTOMono, User.class))
                .switchIfEmpty(notFoundResponse);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<UserDTO> userDTOMono = request.bodyToMono(UserDTO.class);

        return userDTOMono
                .flatMap(userService::create)
                .then(ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(userDTOMono, UserDTO.class))
                .switchIfEmpty(badRequestResponse);
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        String userId = request.pathVariable("id");

        Mono<UserDTO> userDTOMono = request.bodyToMono(UserDTO.class);

        return userDTOMono.flatMap(userDTO ->
                        userService.update(userId, userDTO)
                                .flatMap(updatedUserDTO ->
                                        ServerResponse
                                                .status(HttpStatus.OK)
                                                .contentType(APPLICATION_JSON)
                                                .bodyValue(updatedUserDTO))
                                .switchIfEmpty(notFoundResponse))
                .switchIfEmpty(badRequestResponse);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String userId = request.pathVariable("id");

        return userService.delete(userId)
                .then(ServerResponse
                        .status(HttpStatus.OK)
                        .build())
                .switchIfEmpty(notFoundResponse);
    }

}
