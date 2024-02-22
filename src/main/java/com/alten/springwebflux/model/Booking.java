package com.alten.springwebflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    private String id;
    @DateTimeFormat(pattern = "MM/dd/YYYY")
    private LocalDate date;
    @DateTimeFormat(pattern = "HH/mm/ss")
    private LocalTime time;
    @DocumentReference
    private User user;
}