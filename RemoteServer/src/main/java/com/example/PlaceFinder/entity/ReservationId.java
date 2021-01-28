package com.example.PlaceFinder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class ReservationId implements Serializable {

    private static final long serialVersionUID = 7500519827015353956L;
    @Column
    private String userId;
    @Column
    private Integer slotId;
    @Column
    private String roomId;
    @Column
    private Date reservationDate;

}
