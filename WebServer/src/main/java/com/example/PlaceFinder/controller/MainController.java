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
