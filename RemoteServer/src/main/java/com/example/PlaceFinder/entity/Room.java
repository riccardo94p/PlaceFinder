package com.example.PlaceFinder.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@Table(name = "room")
public class Room implements Serializable {
    @Id
    private String idRoom;

    private int numSeats;

    private float capacity;

/*
    @OneToMany(mappedBy = "room") //references room field in the Slot class
    private List<Slot> slots = new ArrayList<>();

    @OneToMany(mappedBy = "room") //references room field in the Reservation class
    private List<Reservation> reservations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        Room that = (Room) o;
        return Objects.equals( idRoom, that.getIdRoom() );
    }

    @Override
    public int hashCode() {
        return 31;
    }*/
}
