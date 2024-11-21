package com.dustopia.karaoke.service;

import com.dustopia.karaoke.model.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {

    List<Room> getAllRooms();

    void addRoom(Room room);

}
