package com.alten.springwebflux.router;

import com.alten.springwebflux.handler.BookingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class BookingRouter {
    private final BookingHandler bookingHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunctionBooking() {
        return route()
                .path("/bookings", builder -> builder
                        .GET("", bookingHandler::getAllBookings)
                        .GET("/{id}", bookingHandler::getBookingById)
                        .GET("/dateRange/", bookingHandler::getBookingByDateRange)
                        .POST("", bookingHandler::createBooking)
                        .PUT("/{id}", bookingHandler::updateBooking)
                        .DELETE("/{id}", bookingHandler::deleteBooking))
                .build();
    }
}
