package com.dustopia.karaoke.repository;

import com.dustopia.karaoke.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}
