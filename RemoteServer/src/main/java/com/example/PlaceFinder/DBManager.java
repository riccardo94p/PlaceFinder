package com.example.PlaceFinder;
import com.example.PlaceFinder.entity.Reservation;
import com.example.PlaceFinder.entity.User;

import java.util.*;

public interface DBManager {

    //void setup();
    void exit();
    List<User> getUser();
    boolean login(String username, String password);
    void insertUser(User u);
    List<Reservation> browseUserReservations(String userid);
    String addRoom(String idRoom, int numSeats, float capacity);
}
