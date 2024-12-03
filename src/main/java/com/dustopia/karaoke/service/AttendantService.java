package com.dustopia.karaoke.service;

import com.dustopia.karaoke.model.Attendant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttendantService {

    List<Attendant> getAllAvailableAttendants();

    void assignAttendants(List<Attendant> attendants);

}
