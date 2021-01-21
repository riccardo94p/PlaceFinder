package com.example.WebServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @Autowired
    private ApplicationContext ctx;

    @GetMapping("/test")
    public String test() {
        Hello service = ctx.getBean(Hello.class);
        String result = service.getHelloWorld();
        //System.out.println("[RISPOSTA]: "+result);
        return result;
    }
}
