package com.example.WebServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private ApplicationContext ctx;

    @GetMapping("/test")
    public @ResponseBody String test() {
        DBManager service = ctx.getBean(DBManager.class);
        List<User> result = service.getUser();
        System.out.println("[RISPOSTA]: "+result);
        return "";
    }
}
