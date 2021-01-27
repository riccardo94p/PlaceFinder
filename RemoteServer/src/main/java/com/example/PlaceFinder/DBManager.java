package com.example.PlaceFinder;
import com.example.PlaceFinder.entity.Reservation;
import com.example.PlaceFinder.entity.User;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

public interface DBManager {
    
    void exit();
    boolean login(String username, String password);
    List<Reservation> browseUserReservations(String userId);
    boolean notifyCovidContact(String userId);
    boolean userReservation(String userid, int slotid, String roomid, Date date);
    boolean professorReservation(String userid, int slotid, String roomid, Date date);
    boolean deleteUserReservation(String userid, int slotid, String roomid, Date date);
    String addRoom(String idRoom, int numSeats, float capacity);
    boolean changeCapacity(String roomid, float capacity);
    BigInteger getNumReservations(Date date, String room, int slot);
}
