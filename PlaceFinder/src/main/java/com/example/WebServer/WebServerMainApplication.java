package com.example.WebServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;


@SpringBootApplication
public class WebServerMainApplication {

    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://localhost:1099/Hello");
        rmiProxyFactory.setServiceInterface(Hello.class);
        return rmiProxyFactory;
    }
    public static void main( String[] args ) {
        //Hello service = SpringApplication.run(PlaceFinder.class, args).getBean(Hello.class);
        //String result = service.getHelloWorld();
        //System.out.println("[RISPOSTA]: "+result);
        SpringApplication.run(WebServerMainApplication.class, args);
    }
}
