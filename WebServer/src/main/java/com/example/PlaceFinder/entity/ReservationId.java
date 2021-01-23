package com.example.PlaceFinder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class ReservationId implements Serializable {

    @Column
    private String userId;
    @Column
    private Integer slotId;
    @Column
    private String roomId;
    @Column
    private Timestamp timeStamp;

    public ReservationId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ReservationId that = (ReservationId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(slotId, that.slotId) && Objects.equals(roomId, that.roomId) && Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, slotId, roomId, timeStamp);
    }
}
