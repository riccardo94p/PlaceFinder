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
public class UserController {

    @Autowired
    private ApplicationContext ctx;

    @GetMapping("/user/{username}")
    public String profileGet(Model model, Principal principal)
    {
        String username = principal.getName();
        model.addAttribute("username", username);

        DBManager service = ctx.getBean(DBManager.class);
        List<Reservation> rList = service.browseUserReservations(username);
        List<ReservationId> rId = new ArrayList<ReservationId>();

        for(Reservation r : rList)
            rId.add(r.getId());

        model.addAttribute("reservation", rId);
        return "user";
    }

}
