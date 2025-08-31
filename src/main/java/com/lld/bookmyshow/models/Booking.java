package com.lld.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Booking extends BaseModel {

    private String bookingNumber;
    @ManyToOne
    private User user;
    // should we have show obj here as we have showSeat obj which has show object? But it will help in faster retrieval of bookings for a show
    @ManyToOne
    private Show show;
    // user is booking seats for a particular show
    @ManyToMany
    private List<ShowSeat> bookedSeats;

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    private double totalAmount;

    @OneToMany
    private List<Payment> payments;
}

/*
Booking ----- User
        Many to One
Booking ----- Show
        Many to One
Booking ----- ShowSeat
        Many to Many
Booking ----- Payment
        One to Many
 */
