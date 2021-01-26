package com.example.PlaceFinder.entity;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
//@Table(name = "user")
public class User implements Serializable {
    //@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    //private Long idUser;

    @Id @Column(name = "username")
    private String idUser;

    private String password;
    private int privilege;
    private boolean covidNotification;
/*
    @OneToMany(mappedBy = "user") //maps the user field in the Reservation class
    private List<Reservation> reservations = new ArrayList<>();

    public User(String idUser, String password, Integer privilege, boolean covidNotification) {
        this.idUser = idUser;
        this.password = password;
        this.privilege = privilege;
        this.covidNotification = covidNotification;
    }*/

    public User() {    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        User that = (User) o;
        return idUser != null && idUser.equals(that.getIdUser());
    }

    @Override
    public int hashCode() {
        return 41;
    }

    public boolean getCovidNotification(){ return this.covidNotification;}
}