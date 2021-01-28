package com.example.PlaceFinder.repository;

import com.example.PlaceFinder.DBManager;
import com.example.PlaceFinder.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class UserRepository {
    //User findByUsername(String username);
    @Autowired
    private ApplicationContext ctx;

    public User findByUsername(String username) {
        DBManager service = ctx.getBean(DBManager.class);
        return service.getUser(username);
    }
}
