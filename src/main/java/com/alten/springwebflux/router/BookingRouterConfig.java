package com.alten.springwebflux.router;

import com.alten.springwebflux.handler.BookingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class BookingRouterConfig {
    private final BookingHandler bookingHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunctionBooking() {
        return RouterFunctions.route()
                .GET("/router/getAllBookings", bookingHandler::getAllBookings)
                .GET("/router/getBookingById/{id}", bookingHandler::getBookingById)
                .POST("/router/createBooking", bookingHandler::createBooking)
                .PUT("/router/updateBooking/{id}", bookingHandler::updateBooking)
                .DELETE("/router/deleteBooking/{id}", bookingHandler::deleteBooking)
                .build();
    }
}
