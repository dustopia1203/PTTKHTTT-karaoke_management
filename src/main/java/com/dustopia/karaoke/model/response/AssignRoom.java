package com.dustopia.karaoke.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AssignRoom {

    private int bookedRoomId;

    private String roomName;

    private LocalDateTime bookingTime;

    private int currentAttendants;

}
