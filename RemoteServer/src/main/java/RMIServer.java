import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiServiceExporter;

public class RMIServer {
    @Bean
    Hello helloService() {
        return new HelloBean();
    }

    @Bean
    RmiServiceExporter exporter(Hello implementation) {
        // Expose a service via RMI. Remote object URL is:
        // rmi://<HOST>:<PORT>/<SERVICE_NAME>
        // 1099 is the default port

        Class<Hello> serviceInterface = Hello.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(RMIServer.class, args);
    }
}
