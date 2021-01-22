package com.example.PlaceFinder;

import com.example.PlaceFinder.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//-Djava.security.manager -Djava.security.policy=/home/riccardo/Scrivania/PlaceFinder/WebServer/myprogram.policy -Djava.rmi.server.codebase=http://localhost:1099/RemoteServer
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
