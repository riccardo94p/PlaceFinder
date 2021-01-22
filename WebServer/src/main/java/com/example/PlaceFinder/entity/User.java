package com.example.PlaceFinder.entity;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class User implements Serializable {
    @Id
    private Integer id;

    private String username;
    private String password;
    private Integer privilege;
    private boolean covidNotification;

    public User(Integer id, String username, String password, Integer privilege, boolean covidNotification) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.privilege = privilege;
        this.covidNotification = covidNotification;
    }

    public User() {
    }
}