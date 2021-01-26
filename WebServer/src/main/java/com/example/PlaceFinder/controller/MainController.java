package com.example.PlaceFinder.controller;

import com.example.PlaceFinder.DBManager;
import com.example.PlaceFinder.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;

import java.util.List;

//-Djava.security.manager -Djava.security.policy=/home/riccardo/Scrivania/PlaceFinder/WebServer/myprogram.policy -Djava.rmi.server.codebase=http://localhost:1099/RemoteServer
@Controller
public class MainController {

    @Autowired
    private ApplicationContext ctx;

    @GetMapping("/browsereservation")
    public @ResponseBody void browsereservation() {
        DBManager service = ctx.getBean(DBManager.class);
        List<Reservation> r = service.browseUserReservations("aaa1");
        for(Reservation x: r){
            System.out.println("Reservations: " + x.getId().getReservationDate() + " - " + x.getId().getRoomId() + " - " + x.getId().getSlotId());
        }
    }

    @GetMapping("/reserve")
    public @ResponseBody void reserve() {
        DBManager service = ctx.getBean(DBManager.class);
        Date d = Date.valueOf("2021-01-27");
        boolean result = service.userReservation("aaa1", 0, "b12", d);
        if(result){
            List<Reservation> r = service.browseUserReservations("aaa1");
        }
    }

    @RequestMapping("/main")
    public String home() {
        return "main";
    }
}
