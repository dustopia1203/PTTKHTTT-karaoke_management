package com.dustopia.karaoke.service.impl;

import com.dustopia.karaoke.model.Attendant;
import com.dustopia.karaoke.repository.AttendantRepository;
import com.dustopia.karaoke.service.AttendantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendantServiceImpl implements AttendantService {

    private final AttendantRepository attendantRepository;

    @Override
    public List<Attendant> getAllAvailableAttendants() {
        return attendantRepository.findAllByAvailable(true);
    }

    @Override
    public void assignAttendants(List<Attendant> attendants) {
        attendants.forEach(attendant -> attendant.setAvailable(false));
        attendantRepository.saveAll(attendants);
    }

}
