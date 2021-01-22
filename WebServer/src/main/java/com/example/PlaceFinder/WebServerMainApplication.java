package com.example.PlaceFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@SpringBootApplication
public class WebServerMainApplication {

    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/DBManager");
        rmiProxyFactory.setServiceInterface(DBManager.class);
        return rmiProxyFactory;
    }
    public static void main( String[] args ) {
        if(System.getSecurityManager()==null)
            System.setSecurityManager(new SecurityManager());

        System.setProperty("java.security.policy","file:/home/riccardo/Scrivania/PlaceFinder/WebServer/myprogram.policy");
        System.out.println("[SECURITY POLICY]: "+System.getProperty("java.security.policy"));
        SpringApplication.run(WebServerMainApplication.class, args);
    }
}
