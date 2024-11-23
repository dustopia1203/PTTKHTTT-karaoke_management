package com.dustopia.karaoke.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_attendant")
public class Attendant extends Employee {

    @Column(name = "available", nullable = false)
    private boolean available;

    @OneToMany(
            mappedBy = "attendant",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<ServingShift> servingShifts;

}
