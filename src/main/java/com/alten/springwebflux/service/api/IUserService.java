package com.alten.springwebflux.service.api;

import com.alten.springwebflux.dto.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<UserDTO> create(UserDTO dto);
    Mono<UserDTO> update(String id, UserDTO dto);
    Flux<UserDTO> getAll();
    Mono<UserDTO> getById(String id);
    Mono<Void> delete(String id);
}
