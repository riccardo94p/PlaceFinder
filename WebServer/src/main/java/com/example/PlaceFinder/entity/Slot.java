package com.example.PlaceFinder.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class Slot implements Serializable {
    @Id
    private Integer id;
    @Id
    private String idRoom;

    private Integer occupiedSeats;
    private boolean status;
}
