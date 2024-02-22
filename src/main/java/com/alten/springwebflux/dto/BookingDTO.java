package com.alten.springwebflux.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class BookingDTO {
    private String id;
    @DateTimeFormat(pattern = "MM/dd/YYYY")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH/mm/ss")
    private LocalTime time;
    private String userId;
}
