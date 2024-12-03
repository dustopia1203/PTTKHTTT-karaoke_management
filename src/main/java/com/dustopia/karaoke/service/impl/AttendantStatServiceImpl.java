package com.dustopia.karaoke.service.impl;

import com.dustopia.karaoke.model.AttendantStat;
import com.dustopia.karaoke.repository.AttendantRepository;
import com.dustopia.karaoke.service.AttendantStatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendantStatServiceImpl implements AttendantStatService {

    private final AttendantRepository attendantRepository;

    @Override
    public List<AttendantStat> getAllAttendantStats(String startDate, String endDate) {
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            LocalDate startDateParsed = LocalDate.parse(startDate);
            startTime = startDateParsed.atStartOfDay();
            LocalDate endDateParsed = LocalDate.parse(endDate);
            endTime = endDateParsed.atTime(23, 59, 59);
        }
        return attendantRepository.findAllAttendantStats(startTime, endTime).stream()
                .map(query -> AttendantStat.builder()
                        .id(query.getId())
                        .username(query.getUsername())
                        .password(query.getPassword())
                        .name(query.getName())
                        .salary(query.getSalary())
                        .available(query.getAvailable())
                        .totalServingTime((float) query.getTotalServingTime() / 60)
                        .note(query.getNote())
                        .build())
                .collect(Collectors.toList());
    }

}
