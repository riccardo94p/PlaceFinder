package com.example.PlaceFinder.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
//@Table(name = "reservation")
public class Reservation implements Serializable {
    @EmbeddedId
    private ReservationId id;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") //maps the userId field in the ReservationId class
    private User user;

    @ManyToOne
    @MapsId("slotId")
    private Slot slot;

    @ManyToOne
    @MapsId("roomId")
    private Room room;
*/
    public Reservation() { }
/*
    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals( user, that.user ) && Objects.equals(slot, that.slot) && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash( user, room, slot);
    }*/
}
