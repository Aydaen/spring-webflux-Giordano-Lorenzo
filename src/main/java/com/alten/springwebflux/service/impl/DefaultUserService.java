package com.alten.springwebflux.service.impl;

import com.alten.springwebflux.dto.UserDTO;
import com.alten.springwebflux.model.User;
import com.alten.springwebflux.repository.UserRepository;
import com.alten.springwebflux.service.api.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Flux<UserDTO> getAll() {
        return userRepository.findAll()
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Mono<UserDTO> getById(String id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public Mono<UserDTO> create(UserDTO dto) {
        User user = modelMapper.map(dto, User.class);
        return userRepository.save(user)
                .map(savedUser -> modelMapper.map(savedUser, UserDTO.class));
    }

    @Override
    public Mono<UserDTO> update(String id, UserDTO dto) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    User updatedUser = modelMapper.map(dto, User.class);
                    updatedUser.setId(existingUser.getId());
                    return userRepository.save(updatedUser);
                })
                .map(updatedUser -> modelMapper.map(updatedUser, UserDTO.class));
    }

    @Override
    public Mono<Void> delete(String id) {
        return userRepository.deleteById(id);
    }
}
