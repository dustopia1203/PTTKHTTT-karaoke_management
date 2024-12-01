package com.dustopia.karaoke.repository;

import com.dustopia.karaoke.model.Attendant;
import com.dustopia.karaoke.model.ServingShift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ServingShiftRepository extends JpaRepository<ServingShift, Integer> {

    List<ServingShift> findAllByAttendant(Attendant attendant);

    List<ServingShift> findAllByAttendantAndStartTimeAfterAndBookedRoom_ReturnTimeBefore(Attendant attendant, LocalDateTime startTimeAfter, LocalDateTime bookedRoomReturnTimeBefore);

}
