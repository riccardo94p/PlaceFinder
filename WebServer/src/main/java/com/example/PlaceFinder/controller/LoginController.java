package com.example.PlaceFinder.controller;

import com.example.PlaceFinder.DBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private ApplicationContext ctx;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView login() {
        //Redirect if logged in
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            for (GrantedAuthority auth :authentication.getAuthorities()) {
                if ("ADMIN".equals(auth.getAuthority())) { //admin-specific page for seat reduction etc
                    System.out.println("An ADMIN has logged in.");
                    return new ModelAndView("forward:/admin");
                } else { //otherwise redirect to normal page
                    System.out.println("A USER/PROFESSOR has logged in.");
                    return new ModelAndView("forward:/main");
                }
            }
        }
        //otherwise redirect to login page
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
}