package com.example.PlaceFinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;

@SpringBootApplication
public class RMIServer {
    @Bean
    DBManager DBManagerService() {
        return new DBManagerImpl();
    }

    @Bean
    RmiServiceExporter dbServiceExporter(DBManager implementation) {
        // Expose a service via RMI. Remote object URL is rmi://<HOST>:<PORT>/<SERVICE_NAME>

        Class<DBManager> serviceInterface = DBManager.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName(serviceInterface.getSimpleName()); //set service name
        exporter.setService(implementation); //set service
        exporter.setServiceInterface(serviceInterface);
        exporter.setRegistryPort(1099);

        return exporter;
    }

    public static void main(String[] args) {
        if(System.getSecurityManager()==null)
            System.setSecurityManager(new SecurityManager());

        System.out.println("[SECURITY POLICY]: "+System.getProperty("java.security.policy"));
        System.setProperty("java.rmi.server.hostname", "192.168.1.101");
        SpringApplication.run(RMIServer.class, args);
    }
}
