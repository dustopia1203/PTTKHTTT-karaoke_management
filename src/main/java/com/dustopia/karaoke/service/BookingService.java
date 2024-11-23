package com.dustopia.karaoke.service;

import com.dustopia.karaoke.model.Booking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {

    List<Booking> getAllUncheckoutBookings();

    void updateBooking(Booking booking);

}