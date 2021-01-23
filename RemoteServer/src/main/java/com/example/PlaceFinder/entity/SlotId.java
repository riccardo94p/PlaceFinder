package com.example.PlaceFinder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class SlotId implements Serializable {
    @Column
    private String idRoom;
    @Column
    private Integer idSlot;

    public SlotId() { }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        SlotId that = (SlotId) o;
        return Objects.equals(idSlot, that.idSlot) &&
                Objects.equals(idRoom, that.idRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSlot, idRoom);
    }*/
}
