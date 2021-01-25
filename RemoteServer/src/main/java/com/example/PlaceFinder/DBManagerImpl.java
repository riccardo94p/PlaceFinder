package com.example.PlaceFinder;

import com.example.PlaceFinder.entity.Reservation;
import com.example.PlaceFinder.entity.User;

import javax.persistence.*;
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

    public void insertUser(User u) {
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            User exists = entityManager.find(User.class, u.getIdUser());
            if(exists != null)
                System.out.println("Error: User already registered!");
            else
                entityManager.persist(u);
            entityManager.getTransaction().commit();

        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the user registration.");
        }
        finally {
            entityManager.close();
        }
    }

    public List<User> getUser() {
        List<User> tmpUsers = null;

        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();

            Query q = entityManager.createNativeQuery("SELECT * FROM User", User.class);
            tmpUsers = q.getResultList();

            entityManager.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("A problem occurred with the DBManager.findUser().");
        }
        finally {
            entityManager.close();
        }
        System.out.println(tmpUsers);
        return tmpUsers;
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

    public List<User> findCovidContact(String userId){
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

    public int updateCovidNotification(List<User> userList, boolean newNotification){
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
            return 1;
        }
        finally {
            entityManager.close();
        }
        return 0;
    }

    public int notifyCovidContact(String userId){
        List<User> r = findCovidContact(userId);
        if(r == null)
            return 0; //no update needed
        int result = updateCovidNotification(r, true);
        return result;
    }



}
