package com.dustopia.karaoke.repository;

import com.dustopia.karaoke.model.Booking;
import com.dustopia.karaoke.model.ServingShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    String queryAllUncheckout = "select b from Booking b inner join BookedRoom br on b.id = br.booking.id where br.returnTime is null";

    String queryAllByServingShift = "select b " +
            "from Booking b " +
            "  inner join BookedRoom br on br.booking.id = b.id  " +
            "  inner join ServingShift ss on ss.bookedRoom.id = br.id " +
            "where ss = :servingShift";

    @Query(value = queryAllUncheckout)
    List<Booking> findAllByUncheckout();

    @Query(value = queryAllByServingShift)
    List<Booking> findAllByServingShift(ServingShift servingShift);

}
