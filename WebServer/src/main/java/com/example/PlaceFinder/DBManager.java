package com.example.PlaceFinder;

import com.example.PlaceFinder.entity.*;

import java.util.*;

public interface DBManager {

    //void setup();
    void exit();
    List<User> getUser();
    boolean login(String username, String password);
    void insertUser(User u);
    List<Reservation> browseUserReservations(String userid);
}
