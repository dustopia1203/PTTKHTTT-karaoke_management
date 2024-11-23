package com.dustopia.karaoke.service.impl;

import com.dustopia.karaoke.model.Booking;
import com.dustopia.karaoke.repository.BookingRepository;
import com.dustopia.karaoke.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public List<Booking> getAllUncheckoutBookings() {
        return bookingRepository.findAllByUncheckout();
    }

    @Override
    public void updateBooking(Booking booking) {

    }

}
