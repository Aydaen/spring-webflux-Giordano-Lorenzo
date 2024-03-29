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

import java.time.LocalDate;

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
    public Flux<BookingDTO> getByDate(String fromDateString) {
        LocalDate fromDate = LocalDate.parse(fromDateString);

            return bookingRepository.findBookingsByDate(fromDate)
                    .map(booking -> modelMapper.map(booking, BookingDTO.class));
    }

    @Override
    public Flux<BookingDTO> getByDateRange(String fromDateString, String toDateString) {
        LocalDate fromDate = LocalDate.parse(fromDateString);
        LocalDate toDate = LocalDate.parse(toDateString);

        return bookingRepository.findBookingsByDateBetween(fromDate, toDate)
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
                    existingBooking.setDate(dto.getDate() == null ? existingBooking.getDate() : dto.getDate());
                    existingBooking.setTime(dto.getTime() == null ? existingBooking.getTime() : dto.getTime());
                    return bookingRepository.save(existingBooking);
                })
                .map(updatedBooking -> modelMapper.map(updatedBooking, BookingDTO.class));
    }

    @Override
    public Mono<Void> delete(String id) {
        return bookingRepository.deleteById(id);
    }
}
