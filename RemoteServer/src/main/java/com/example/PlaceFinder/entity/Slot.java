package com.example.PlaceFinder.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Slot implements Serializable {
    private static final long serialVersionUID = -7662935462663092324L;
    @Id
    private Integer idSlot;

    private Time startTime;
    private Time endTime;
}
