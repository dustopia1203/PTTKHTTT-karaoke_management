package com.dustopia.karaoke.service.impl;

import com.dustopia.karaoke.model.Attendant;
import com.dustopia.karaoke.model.ServingShift;
import com.dustopia.karaoke.repository.ServingShiftRepository;
import com.dustopia.karaoke.service.ServingShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServingShiftServiceImpl implements ServingShiftService {

    private final ServingShiftRepository servingShiftRepository;

    @Override
    public List<ServingShift> getAllServingShiftsOfAttendant(Attendant attendant, String startDate, String endDate) {
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            LocalDate startDateParsed = LocalDate.parse(startDate);
            startTime = startDateParsed.atStartOfDay();
            LocalDate endDateParsed = LocalDate.parse(endDate);
            endTime = endDateParsed.atTime(23, 59, 59);
            return servingShiftRepository.findAllByAttendantAndStartTimeAfterAndBookedRoom_ReturnTimeBefore(attendant, startTime, endTime);
        }
        return servingShiftRepository.findAllByAttendant(attendant);
    }

}
