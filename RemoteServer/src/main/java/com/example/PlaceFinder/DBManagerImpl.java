package com.example.PlaceFinder;

import com.example.PlaceFinder.entity.Reservation;
import com.example.PlaceFinder.entity.Room;
import com.example.PlaceFinder.entity.User;

import javax.persistence.*;

import java.util.Date;
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
        else return true;
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
    public List<Reservation> browseUserReservations(String userid) {
        List<Reservation> r = null;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();

            Query q = entityManager.createNativeQuery("SELECT * FROM placefinder.Reservation WHERE userId=?", Reservation.class);
            q.setParameter(1, userid);

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

    //deletes all reservatons for a given room
    private void deleteReservations(String roomid) {
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();

            Query q = entityManager.createNativeQuery("DELETE FROM Reservation r WHERE r.roomId=? AND r.reservationDate >= CURRENT_DATE()");
            q.setParameter(1, roomid);

            entityManager.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the deleteReservations()");
        }
        finally {
            entityManager.close();
        }
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
        }
        finally {
            entityManager.close();
        }
        return prevCapacity;
    }

    public boolean changeCapacity(String roomid, float capacity) {
        boolean r = true;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();

            float previousCapacity = getRoomCapacity(roomid);
            Query q2 = entityManager.createNativeQuery("UPDATE Room SET capacity=? WHERE (idRoom=?)");
            q2.setParameter(1, capacity);
            q2.setParameter(2, roomid);
            q2.executeUpdate();

            //In seguito a riduzione capienza, delete di tutte le prenotazioni degli utenti
            if(previousCapacity > capacity) {
                deleteReservations(roomid);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the changeCapacity()");
            r = false;
        }
        finally {
            entityManager.close();
        }
        return r;
    }
}