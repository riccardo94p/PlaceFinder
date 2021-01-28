package com.example.PlaceFinder;
import com.example.PlaceFinder.entity.Reservation;
import com.example.PlaceFinder.entity.Room;
import com.example.PlaceFinder.entity.Slot;
import com.example.PlaceFinder.entity.User;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.sql.Date;

public interface DBManager {
    
    void exit();
    User getUser(String username);
    List<User> browseUsers();
    List<Slot> browseSlots();
    Slot findSlotById(int slotId);
    boolean login(String username, String password);
    List<Reservation> browseUserReservations(String userId);
    boolean notifyCovidContact(String userId);
    boolean userReservation(String userid, int slotid, String roomid, Date date);
    boolean professorReservation(String userid, int slotid, String roomid, Date date);
    boolean deleteUserReservation(String userid, int slotid, String roomid, Date date);
    List<Room> getRooms();
    String addRoom(String idRoom, int numSeats, float capacity);
    boolean changeCapacity(String roomid, float capacity);
    int getAvailableSeats(String roomid);
    BigInteger getNumReservations(Date date, String room, int slot);

    List<Object> getAvailabilityList(Date date, int slot);
}
