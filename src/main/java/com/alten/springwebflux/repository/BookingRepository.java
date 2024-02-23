package com.alten.springwebflux.repository;

import com.alten.springwebflux.model.Booking;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {
    public Flux<Booking> findBookingsByDate(LocalDate fromDate);
    @Query("{'date': {$gte: ?0, $lte: ?1}}")
    public Flux<Booking> findBookingsByDateBetween(LocalDate fromDate, LocalDate toDate);
}
