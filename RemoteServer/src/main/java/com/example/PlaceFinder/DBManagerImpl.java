package com.example.PlaceFinder;

import com.example.PlaceFinder.entity.Reservation;
import com.example.PlaceFinder.entity.Room;
import com.example.PlaceFinder.entity.User;

import javax.persistence.*;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class DBManagerImpl implements DBManager {
    private static EntityManager entityManager;
    private static EntityManagerFactory factory;

    public DBManagerImpl() {
        factory = Persistence.createEntityManagerFactory("placefinder");
    }

    public void exit() {
        factory.close();
    }

    public boolean login(String username, String password) {
        List<User> tmpUsers = null;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();

            Query q = entityManager.createNativeQuery("SELECT * FROM User u WHERE u.username=? AND u.password=?", User.class);
            q.setParameter(1, username);
            q.setParameter(2, password);
            tmpUsers = q.getResultList();

            entityManager.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the login.");
        }
        finally {
            entityManager.close();
        }
        if(tmpUsers == null) return false;
        if(tmpUsers.get(0).getCovidNotification())
            updateCovidNotification(tmpUsers, false);
        return true;
    }

    public boolean userReservation(String userid, int slotid, String roomid, Date date) {
        boolean r = true;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("INSERT INTO Reservation VALUES (?,?,?,?)");
            q.setParameter(1, userid);
            q.setParameter(2, slotid);
            q.setParameter(3, roomid);
            q.setParameter(4, date);
            q.executeUpdate();
            entityManager.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the userReservation()");
            r = false;
        }
        finally {
            entityManager.close();
        }
        return r;
    }

    public boolean professorReservation(String userid, int slotid, String roomid, Date date) {
        //TODO: bisogna controllare che non sia già prenotata da un altro prof
        userReservation(userid, slotid, roomid, date);
        boolean r = true;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("DELETE FROM Reservation WHERE userId != ? AND slotId=? AND roomId=? AND reservationDate=?");
            q.setParameter(1, userid);
            q.setParameter(2, slotid);
            q.setParameter(3, roomid);
            q.setParameter(4, date);

            entityManager.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the userReservation()");
            r = false;
        }
        finally {
            entityManager.close();
        }
        return r;
    }

    public BigInteger getNumReservations(Date date, String room, int slot){
        BigInteger reservations;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("SELECT COUNT(*) FROM Reservation R WHERE R.reservationDate = ? AND R.roomId = ? AND R.slotId = ?;");
            q.setParameter(1, date);
            q.setParameter(2, room);
            q.setParameter(3, slot);
            reservations = (BigInteger) q.getSingleResult();
            entityManager.getTransaction().commit();
            System.out.println("Il numero è: " + reservations);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the getNumReservations()");
            reservations = BigInteger.valueOf(-1);
        }
        finally {
            entityManager.close();
        }
        return reservations;
    }

    public boolean deleteUserReservation(String userid, int slotid, String roomid, Date date) {
        boolean r = true;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("DELETE FROM Reservation WHERE userId=? AND slotId=? AND roomId=? AND reservationDate=?");
            q.setParameter(1, userid);
            q.setParameter(2, slotid);
            q.setParameter(3, roomid);
            q.setParameter(4, date);
            q.executeUpdate();
            entityManager.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the userReservation()");
            r = false;
        }
        finally {
            entityManager.close();
        }
        return r;
    }

    // get user reservations
    public List<Reservation> browseUserReservations(String userId) {
        List<Reservation> r = null;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("SELECT * FROM Reservation R WHERE R.userId = ?;", Reservation.class);
            q.setParameter(1, userId);
            r = q.getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the browseUserReservations()");
        }
        finally {
            entityManager.close();
        }
        return r;
    }


    private List<User> findCovidContact(String userId){
        List<User> r = null;
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("SELECT DISTINCT UU.*\n" +
                    "FROM User UU\n" +
                    "INNER JOIN (SELECT RR.*\n" +
                                "FROM Reservation RR \n" +
                                "NATURAL JOIN ( SELECT T.slotId, T.roomId, T.reservationDate\n" +
                                                "FROM Reservation T\n" +
                                                "WHERE T.userId = ? AND\n" +
                                                "T.reservationDate >= (CURRENT_DATE() - INTERVAL 1 WEEK )) as T) as P\n" +
                    "ON UU.username = P.userId", User.class);
            q.setParameter(1, userId);
            r = q.getResultList();
            entityManager.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the findCovidContact()");
        }
        finally {
            entityManager.close();
        }
        return r;
    }

    //reserved to admin only
    public String addRoom(String idRoom, int numSeats, float capacity) {
        String result = "";
        Room r = new Room();
        r.setIdRoom(idRoom);
        r.setNumSeats(numSeats);
        r.setCapacity(capacity);

        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Room exists = entityManager.find(Room.class, idRoom);
            if(exists != null)
                result ="Room already registered.";
            else {
                entityManager.persist(r);
                result = "Room successfully added.";
            }
            entityManager.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            result = "A problem occurred with the room addition.";
        }
        finally {
            entityManager.close();
        }
        return result;
    }

    private boolean updateCovidNotification(List<User> userList, boolean newNotification){
        try{
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            for(User x : userList){
                x.setCovidNotification(newNotification);
                entityManager.merge(x);
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the updateCovidNotification()");
            return false;
        }
        return true;
    }

    //deletes all reservatons for a given room
    private void deleteReservations(String roomid) {
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("DELETE FROM Reservation r WHERE r.roomId=? AND r.reservationDate >= CURRENT_DATE()");
            q.setParameter(1, roomid);
            q.executeUpdate();
            entityManager.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the deleteReservations()");
        }
        finally {
            entityManager.close();
        }
    }

    public boolean notifyCovidContact(String userId){
        List<User> r = findCovidContact(userId);
        if(r == null)
            return true; //no update needed
        boolean result = updateCovidNotification(r, true);
        return result;
    }

    //get current capacity for a specific room
    private float getRoomCapacity(String roomid) {
        float prevCapacity = 0;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            prevCapacity = entityManager.find(Room.class, roomid).getCapacity();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the getRoomCapacity()");
            prevCapacity = -1.0f;
        }
        finally {
            entityManager.close();
        }

        return prevCapacity;
    }

    public boolean changeCapacity(String roomid, float capacity) {
        boolean r = true;
        float previousCapacity = getRoomCapacity(roomid);
        if(previousCapacity < 0.0f)
            return false;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q2 = entityManager.createNativeQuery("UPDATE Room SET capacity=? WHERE (idRoom=?)");
            q2.setParameter(1, capacity);
            q2.setParameter(2, roomid);
            q2.executeUpdate();
            entityManager.getTransaction().commit();
            //In seguito a riduzione capienza, delete di tutte le prenotazioni degli utenti
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the changeCapacity()");
            r = false;
        }
        finally {
            entityManager.close();
        }
        if(previousCapacity > capacity) {
            deleteReservations(roomid);
        }
        return r;
    }
}
