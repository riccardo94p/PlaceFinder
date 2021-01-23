package com.example.PlaceFinder.controller;

import com.example.PlaceFinder.DBManager;
import com.example.PlaceFinder.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//-Djava.security.manager -Djava.security.policy=/home/riccardo/Scrivania/PlaceFinder/WebServer/myprogram.policy -Djava.rmi.server.codebase=http://localhost:1099/RemoteServer
@Controller
public class MainController {

    @Autowired
    private ApplicationContext ctx;

    @GetMapping("/test")
    public @ResponseBody String test() {
        DBManager service = ctx.getBean(DBManager.class);
        //List<User> result = service.getUser();
        List<Reservation> r = service.browseUserReservations("aaa1");
        System.out.println("[RISPOSTA 1]: "+r);
        return "";
    }

    @RequestMapping("/main")
    public String home() {
        return "main";
    }
}
