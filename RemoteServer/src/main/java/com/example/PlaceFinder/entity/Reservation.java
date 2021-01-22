package com.example.PlaceFinder.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
public class Reservation implements Serializable {
    @Id
    private Integer userId;
    @Id
    private Integer slotId;
    @Id
    private String roomId;

    private Timestamp timeStamp;
}
