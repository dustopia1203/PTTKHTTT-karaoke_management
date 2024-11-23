package com.dustopia.karaoke.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_serving_shift")
public class ServingShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private LocalDateTime startTime;

    private String note;

    @ManyToOne
    @JoinColumn(name = "attendant_id")
    private Attendant attendant;

    @ManyToOne
    @JoinColumn(name = "booked_room_id")
    private BookedRoom bookedRoom;

}
