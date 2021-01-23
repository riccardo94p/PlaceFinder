package com.example.PlaceFinder.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
//@Table(name = "slot")
public class Slot implements Serializable {
    @EmbeddedId
    private SlotId idSlot;

    private int occupiedSeats;
    private boolean status;
/*
    @ManyToOne
    @MapsId("idRoom") //maps idRoom field in SlotId class
    private Room room;

    @OneToMany(mappedBy = "slot")
    private List<Reservation> reservations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Slot that = (Slot) o;
        return Objects.equals( room, that.room );// && Objects.equals( book, that.book );
    }

    @Override
    public int hashCode() {
        return Objects.hash(room);
    }*/
}
