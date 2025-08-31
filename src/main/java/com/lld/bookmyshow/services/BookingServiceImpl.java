package com.lld.bookmyshow.services;

import com.lld.bookmyshow.exceptions.SeatNotAvailableException;
import com.lld.bookmyshow.exceptions.ShowNotFoundException;
import com.lld.bookmyshow.exceptions.UserNotFoundException;
import com.lld.bookmyshow.models.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.lld.bookmyshow.repositories.BookingRepository;
import com.lld.bookmyshow.repositories.ShowRepository;
import com.lld.bookmyshow.repositories.ShowSeatRepository;
import com.lld.bookmyshow.repositories.UserRepository;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculator priceCalculator;
    private BookingRepository bookingRepository;

    public BookingServiceImpl(UserRepository userRepository, ShowRepository showRepository, ShowSeatRepository showSeatRepository,
                              PriceCalculator priceCalculator, BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
        this.priceCalculator = priceCalculator;
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking createBooking(Long userId, Long showId, List<Long> showSeatIds) throws UserNotFoundException,
            ShowNotFoundException, SeatNotAvailableException {
        // Implementation logic to create a booking
        /*
        1. Get the user by userId
        2. Get the show by showId
        3. Get the show seats by showSeatIds
        ---------- TAKE LOCK ON THE SEATS -----------
        4. Check if the seats are available
        5. If not, throw exception
        6. If available, mark them as BLOCKED
        --------- RELEASE LOCK ON THE SEATS -----------
        7. Save the changes to the database
        8. Create a booking object and set its attributes and mark the status as PENDING
        9. Save the booking object to the database
        10. Return the booking object
         */

        // 1. Get the user by userId
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        // 2. Get the show by showId
        Show show = showRepository.findById(showId).orElseThrow(() -> new ShowNotFoundException("Show not found with id: " + showId));
        // 3. Get the show seats by showSeatIds
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        // 4. Check if the seats are available
        for(ShowSeat showSeat : showSeats) {
            // 5. If not, throw exception
            if(!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
                throw new SeatNotAvailableException("Seat not available with id: " + showSeat.getId());
            }
        }

        // 6. If available, mark them as BLOCKED
        for(ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            // 7. save the changes to the db.
            showSeatRepository.save(showSeat);
        }
        // 8. Create a booking object and set its attributes and mark the status as PENDING
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setBookedSeats(showSeats);
        // calculate total amount
        booking.setTotalAmount(priceCalculator.calculatePrice(show, showSeatIds));
        return bookingRepository.save(booking);
    }
}
