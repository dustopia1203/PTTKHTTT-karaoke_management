package com.dustopia.karaoke.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_booked_room")
public class BookedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "current_attendant", nullable = false)
    private int currentAttendant;

    @Column(name = "return_time")
    private LocalDateTime returnTime;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @OneToMany(
            mappedBy = "bookedRoom",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<ServingShift> servingShifts;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
