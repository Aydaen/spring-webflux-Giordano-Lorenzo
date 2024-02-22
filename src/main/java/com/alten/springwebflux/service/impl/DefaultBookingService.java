package com.alten.springwebflux.service.impl;

import com.alten.springwebflux.dto.BookingDTO;
import com.alten.springwebflux.model.Booking;
import com.alten.springwebflux.repository.BookingRepository;
import com.alten.springwebflux.service.api.IBookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultBookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    @Override
    public Flux<BookingDTO> getAll() {
        return bookingRepository.findAll()
                .map(booking -> modelMapper.map(booking, BookingDTO.class));
    }

    @Override
    public Mono<BookingDTO> getById(String id) {
        return bookingRepository.findById(id)
                .map(booking -> modelMapper.map(booking, BookingDTO.class));
    }

    @Override
    public Mono<BookingDTO> create(BookingDTO dto) {
        Booking booking = modelMapper.map(dto, Booking.class);
        return bookingRepository.save(booking)
                .map(savedBooking -> modelMapper.map(savedBooking, BookingDTO.class));
    }

    @Override
    public Mono<BookingDTO> update(String id, BookingDTO dto) {
        return bookingRepository.findById(id)
                .flatMap(existingBooking -> {
                    Booking updatedBooking = modelMapper.map(dto, Booking.class);
                    updatedBooking.setId(existingBooking.getId());
                    return bookingRepository.save(updatedBooking);
                })
                .map(updatedBooking -> modelMapper.map(updatedBooking, BookingDTO.class));
    }

    @Override
    public Mono<Void> delete(String id) {
        return bookingRepository.deleteById(id);
    }
}
