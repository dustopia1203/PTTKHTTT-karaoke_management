package com.dustopia.karaoke.service;

import com.dustopia.karaoke.model.Attendant;
import com.dustopia.karaoke.model.ServingShift;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServingShiftService {

    List<ServingShift> getAllServingShiftsOfAttendant(Attendant attendant, String startDate, String endDate);

}
