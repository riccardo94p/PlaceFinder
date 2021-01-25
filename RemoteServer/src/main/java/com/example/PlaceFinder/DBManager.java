package com.example.PlaceFinder;
import com.example.PlaceFinder.entity.Reservation;
import com.example.PlaceFinder.entity.User;

import java.util.*;

public interface DBManager {
    
    void exit();
    boolean login(String username, String password);
    boolean userReservation(String userid, int slotid, String roomid, Date date);
    boolean professorReservation(String userid, int slotid, String roomid, Date date);
    boolean deleteUserReservation(String userid, int slotid, String roomid, Date date);
    List<Reservation> browseUserReservations(String userid);
    String addRoom(String idRoom, int numSeats, float capacity);
    boolean changeCapacity(String roomid, float capacity);
}
