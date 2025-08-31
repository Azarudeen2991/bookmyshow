package com.lld.bookmyshow.controllers;

import com.lld.bookmyshow.dtos.CreateBookingRequestDto;
import com.lld.bookmyshow.dtos.CreateBookingResponseDto;
import com.lld.bookmyshow.dtos.ResponseStatus;
import com.lld.bookmyshow.models.Booking;
import com.lld.bookmyshow.services.BookingService;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public CreateBookingResponseDto createBooking(CreateBookingRequestDto createBookingRequestDto) {
        CreateBookingResponseDto responseDto = new CreateBookingResponseDto();
        try {
            var booking = bookingService.createBooking(
                    createBookingRequestDto.getUserId(),
                    createBookingRequestDto.getShowId(),
                    createBookingRequestDto.getShowSeatIds()
            );
            responseDto.setBooking(booking);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
