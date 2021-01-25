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
    List<Reservation> browseUserReservations(String userId);
    List<User> findCovidContact (String userId);
    int updateCovidNotification(List<User> userList, boolean newNotification);
    int notifyCovidContact(String userId);
}
