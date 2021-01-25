package com.example.PlaceFinder;

import com.example.PlaceFinder.entity.Reservation;
import com.example.PlaceFinder.entity.Room;
import com.example.PlaceFinder.entity.User;
import org.hibernate.Hibernate;

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
        else return true;
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
    public List<Reservation> browseUserReservations(String userid) {
        List<Reservation> r = null;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            Query q = entityManager.createNativeQuery("SELECT * FROM placefinder.Reservation WHERE userId = :userId;", Reservation.class);
            q.setParameter("userId", userid);
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

}
