package com.dustopia.karaoke.service;

import com.dustopia.karaoke.model.AttendantStat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendantStatService {

    List<AttendantStat> getAllAttendantStats(String startDate, String endDate);

}
