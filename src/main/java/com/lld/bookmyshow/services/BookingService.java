package com.lld.bookmyshow.services;

import com.lld.bookmyshow.exceptions.SeatNotAvailableException;
import com.lld.bookmyshow.exceptions.ShowNotFoundException;
import com.lld.bookmyshow.exceptions.UserNotFoundException;
import com.lld.bookmyshow.models.Booking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface BookingService {

    @Transactional(isolation = Isolation.SERIALIZABLE)
    Booking createBooking(Long userId, Long showId, List<Long> showSeatIds) throws UserNotFoundException,
            ShowNotFoundException, SeatNotAvailableException;
}
