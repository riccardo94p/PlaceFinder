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
public class Room implements Serializable {
    private static final long serialVersionUID = -4128111638414757299L;
    @Id
    private String idRoom;

    private int numSeats;
    private float capacity;
}

/*
If a serializable class does not explicitly declare a serialVersionUID, then the serialization runtime will calculate a default serialVersionUID
and there is no guarantee that different machines will generate the same id;
 */
