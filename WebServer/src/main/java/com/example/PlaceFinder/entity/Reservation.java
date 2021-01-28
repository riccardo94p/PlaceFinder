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
@NoArgsConstructor
public class Reservation implements Serializable {
    private static final long serialVersionUID = -4126471220728424836L;
    @EmbeddedId
    private ReservationId id;
}
