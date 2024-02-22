package com.alten.springwebflux.repository;

import com.alten.springwebflux.model.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {
}
