package com.alten.springwebflux.service.api;

import com.alten.springwebflux.dto.BookingDTO;
import org.springframework.cglib.core.Local;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface IBookingService {
    Mono<BookingDTO> create(BookingDTO dto);
    Mono<BookingDTO> update(String id, BookingDTO dto);
    Flux<BookingDTO> getAll();
    Mono<BookingDTO> getById(String id);
    Flux<BookingDTO> getByDate(String fromDateString);
    Flux<BookingDTO> getByDateRange(String fromDateString, String toDateString);
    Mono<Void> delete(String id);
}
