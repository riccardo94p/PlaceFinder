package com.example.PlaceFinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class WebServerMainApplication {

    @Bean
    RmiProxyFactoryBean service() {
        RmiProxyFactoryBean rmiProxyFactory = new RmiProxyFactoryBean();
        rmiProxyFactory.setServiceUrl("rmi://192.168.1.101:1099/DBManager");
        rmiProxyFactory.setServiceInterface(DBManager.class);
        return rmiProxyFactory;
    }

    public static void main( String[] args ) {
        if(System.getSecurityManager()==null)
            System.setSecurityManager(new SecurityManager());

        System.out.println("[SECURITY POLICY]: "+System.getProperty("java.security.policy"));
        SpringApplication.run(WebServerMainApplication.class, args);
    }
}
