package com.example.RemoteServer;

import javax.persistence.*;
import java.util.List;

public class DBManagerImpl implements DBManager {
    private static EntityManager entityManager;
    private static EntityManagerFactory factory;
    private String loggedUser=null;

    public DBManagerImpl() {
        factory = Persistence.createEntityManagerFactory("placefinder");
    }

    public void exit() {
        factory.close();
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


    /*@Autowired
    private UserRepository userRepository;

    private int howManyTimes = 0;

    public int howManyTimes() {
        return howManyTimes;
    }

    public String getHelloWorld() {
        howManyTimes++;
        return "Hello World! "+howManyTimes+" calls.";
    }

    public String getUsers() {
        userRepository.findAll().forEach(user->{
            System.out.println(user.getUsername());
        });
        return "";
    }*/
}
