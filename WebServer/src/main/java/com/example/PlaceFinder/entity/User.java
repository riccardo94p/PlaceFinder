package com.example.PlaceFinder.entity;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
//@Table(name = "user")
public class User implements Serializable {

    @Id
    private String username;

    private String password;
    private String role;
    private boolean covidNotification;


    public User() {    }

    /*@Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        User that = (User) o;
        return idUser != null && idUser.equals(that.getIdUser());
    }*/

    @Override
    public int hashCode() {
        return 41;
    }

    public boolean getCovidNotification(){ return this.covidNotification;}
}