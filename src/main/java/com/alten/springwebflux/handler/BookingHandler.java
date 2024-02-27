package com.alten.springwebflux.handler;

import com.alten.springwebflux.dto.BookingDTO;
import com.alten.springwebflux.service.api.IBookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class BookingHandler {
    private final IBookingService bookingService;
    private final BookingWebSocketHandler bookingWebSocketHandler;
    private final ModelMapper modelMapper;

    Mono<ServerResponse> notFoundResponse = ServerResponse.notFound().build();
    Mono<ServerResponse> badRequestResponse = ServerResponse.badRequest().build();

    public Mono<ServerResponse> getAllBookings(ServerRequest request) {
        Flux<BookingDTO> bookingFlux = bookingService.getAll();

        return ServerResponse
                .status(HttpStatus.OK)
                .body(bookingFlux, BookingDTO.class);
    }

    public Mono<ServerResponse> getBookingById(ServerRequest request) {
        String bookingId = request.pathVariable("id");

        Mono<BookingDTO> bookingDTOMono = bookingService.getById(bookingId);

        return bookingDTOMono.flatMap(userDTO ->
                        ServerResponse
                                .status(HttpStatus.OK)
                                .contentType(APPLICATION_JSON)
                                .body(bookingDTOMono, BookingDTO.class))
                .switchIfEmpty(notFoundResponse);
    }

    public Mono<ServerResponse> createBooking(ServerRequest request) {
        Mono<BookingDTO> bookingDTOMono = request.bodyToMono(BookingDTO.class);

        return bookingDTOMono
                .flatMap(bookingService::create)
                .doOnSuccess(bookingDTO -> bookingWebSocketHandler.sendMessage("New booking created with id: " + bookingDTO.getId()))
                .flatMap(bookingDTO -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(APPLICATION_JSON)
                        .bodyValue(bookingDTO))
                .switchIfEmpty(badRequestResponse);
    }

    public Mono<ServerResponse> updateBooking(ServerRequest request) {
        String bookingId = request.pathVariable("id");

        Mono<BookingDTO> bookingDTOMono = request.bodyToMono(BookingDTO.class);

        return bookingDTOMono.flatMap(bookingDTO ->
                        bookingService.update(bookingId, bookingDTO)
                                .flatMap(updatedBookingDTO ->
                                        ServerResponse
                                                .status(HttpStatus.OK)
                                                .contentType(APPLICATION_JSON)
                                                .bodyValue(updatedBookingDTO))
                                .switchIfEmpty(notFoundResponse))
                .switchIfEmpty(badRequestResponse);
    }

    public Mono<ServerResponse> deleteBooking(ServerRequest request) {
        String bookingId = request.pathVariable("id");

        return bookingService.delete(bookingId)
                .doOnSuccess(bookingDTO -> bookingWebSocketHandler.sendMessage("Booking with id " + bookingId + " has been deleted"))
                .then(ServerResponse
                        .status(HttpStatus.OK)
                        .build())
                .switchIfEmpty(notFoundResponse);
    }

    public Mono<ServerResponse> getBookingByDateRange(ServerRequest request) {
        Optional<String> fromDate = request.queryParam("fromDate");
        Optional<String> toDate = request.queryParam("toDate");

        if (fromDate.isEmpty()) {
            return ServerResponse
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        if (toDate.isEmpty()) {
            Flux<BookingDTO> bookingDTOFlux = bookingService.getByDate(fromDate.get());

            return ServerResponse
                    .status(HttpStatus.OK)
                    .body(bookingDTOFlux, BookingDTO.class);

        }

        Flux<BookingDTO> bookingDTOFlux = bookingService.getByDateRange(fromDate.get(), toDate.get());

        return ServerResponse
                .status(HttpStatus.OK)
                .body(bookingDTOFlux, BookingDTO.class);
    }
}
