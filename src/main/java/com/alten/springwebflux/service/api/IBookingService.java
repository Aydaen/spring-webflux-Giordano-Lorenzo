package com.alten.springwebflux.service.api;

import com.alten.springwebflux.dto.BookingDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBookingService {
    Mono<BookingDTO> create(BookingDTO dto);
    Mono<BookingDTO> update(String id, BookingDTO dto);
    Flux<BookingDTO> getAll();
    Mono<BookingDTO> getById(String id);
    Mono<Void> delete(String id);
}
