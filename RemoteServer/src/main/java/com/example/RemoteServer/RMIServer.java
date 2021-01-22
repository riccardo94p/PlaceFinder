package com.example.RemoteServer;

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
    RmiServiceExporter exporter(DBManager implementation) {
        // Expose a service via RMI. Remote object URL is:
        // rmi://<HOST>:<PORT>/<SERVICE_NAME>
        // 1099 is the default port

        Class<DBManager> serviceInterface = DBManager.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    public static void main(String[] args) {
        if(System.getSecurityManager()==null)
            System.setSecurityManager(new SecurityManager());

        System.setProperty("java.security.policy","file:/home/riccardo/Scrivania/PlaceFinder/RemoteServer/myprogram.policy");
        System.out.println("[SECURITY POLICY]: "+System.getProperty("java.security.policy"));
        SpringApplication.run(RMIServer.class, args);
    }
}
