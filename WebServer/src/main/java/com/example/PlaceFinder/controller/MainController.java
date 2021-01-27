package com.example.PlaceFinder.controller;

import com.example.PlaceFinder.DBManager;
import com.example.PlaceFinder.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Date;

import java.util.ArrayList;
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
    public String main(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);//loggedUser.getUsername());
        return "main";
    }

    @RequestMapping("/admin")
    public String admin(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);

        DBManager service = ctx.getBean(DBManager.class);
        List<Room> rooms = service.getRooms();

        List<User> users = service.browseUsers();

        model.addAttribute("rooms", rooms);
        model.addAttribute("users", users);
        return "admin";
    }

    @RequestMapping("/addRoom")
    public String addRoom(Model model, @RequestParam String id,
                                      @RequestParam int numseats,
                                      @RequestParam float capacity) {
        System.out.println("[DBG]: /addRoom parameters "+id+" "+numseats+" "+capacity);

        //Of course this is not ideal nor secure: there is no trace of which admin performed this operation.
        //This is only meant for demonstration purposes
        DBManager service = ctx.getBean(DBManager.class);
        service.addRoom(id, numseats, capacity);

        return "admin";
    }

    @RequestMapping("/editCapacity")
    public String editCapacity(Model model, @RequestParam String id, @RequestParam float capacity) {

        //Of course this is not ideal nor secure: there is no trace of which admin performed this operation.
        //This is only meant for demonstration purposes
        DBManager service = ctx.getBean(DBManager.class);
        service.changeCapacity(id, capacity);

        return "admin";
    }

    @RequestMapping("/notifyCovid")
    public String notifyCovid(Model m, @RequestParam String id) {
        //Of course this is not ideal nor secure: there is no trace of which admin performed this operation.
        //This is only meant for demonstration purposes
        DBManager service = ctx.getBean(DBManager.class);
        service.notifyCovidContact(id);
        return "admin";
    }

    //for 403 access denied page
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            model.addObject("username", userDetail.getUsername());
        }

        model.setViewName("403");
        return model;
    }
}
