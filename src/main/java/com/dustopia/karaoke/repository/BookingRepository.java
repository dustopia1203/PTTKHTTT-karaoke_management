package com.dustopia.karaoke.repository;

import com.dustopia.karaoke.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    String queryAllUncheckout = "select b from Booking b join BookedRoom br on b.id = br.booking.id where br.returnTime is null";

    @Query(value = queryAllUncheckout)
    List<Booking> findAllByUncheckout();

}
