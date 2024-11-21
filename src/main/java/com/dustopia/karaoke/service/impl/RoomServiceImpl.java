package com.dustopia.karaoke.service.impl;

import com.dustopia.karaoke.model.Room;
import com.dustopia.karaoke.repository.RoomRepository;
import com.dustopia.karaoke.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public void addRoom(Room room) {
        roomRepository.save(room);
    }

}
