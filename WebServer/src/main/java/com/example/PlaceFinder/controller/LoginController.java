package com.example.PlaceFinder.controller;

import com.example.PlaceFinder.DBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @Autowired
    private ApplicationContext ctx;
    //@RequestMapping(value = "/", method = RequestMethod.GET)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password) {
        DBManager service = ctx.getBean(DBManager.class);
        boolean result = service.login(username, password);
        System.out.println("[RISPOSTA]: "+result);

        return new ModelAndView("forward:/main");
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView login() {
        //Redirect if logged in
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            for (GrantedAuthority auth :authentication.getAuthorities()) {
                if ("ADMIN".equals(auth.getAuthority())) { //admin-specific page for seat reduction etc
                    return new ModelAndView("forward:/admin");
                }
                if ("USER".equals(auth.getAuthority())) { //otherwise redirect to normal page
                    return new ModelAndView("forward:/match");
                }
            }
        }*/
        //otherwise redirect to login page
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
