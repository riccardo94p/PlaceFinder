package com.example.PlaceFinder.entity;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -1840631036252387600L;
    @Id
    private String username;

    private String password;
    private String role;
    private boolean covidNotification;

    @Override
    public int hashCode() {
        return 41;
    }

    public boolean getCovidNotification(){ return this.covidNotification;}
}