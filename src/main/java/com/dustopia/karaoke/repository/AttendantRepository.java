package com.dustopia.karaoke.repository;

import com.dustopia.karaoke.model.Attendant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendantRepository extends JpaRepository<Attendant, Integer> {

    List<Attendant> findAllByAvailable(boolean available);

}
