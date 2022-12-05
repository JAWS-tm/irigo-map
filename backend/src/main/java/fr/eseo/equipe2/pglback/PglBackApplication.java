package fr.eseo.equipe2.pglback;

import fr.eseo.equipe2.pglback.consumeApi.IrigoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2 // /swagger-ui/index.html
public class PglBackApplication extends SpringBootServletInitializer {
    @Autowired
    IrigoApi irigoApi;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PglBackApplication.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        irigoApi.fetchAllStop();
        irigoApi.fetchBusLines();
    }

    public static void main(String[] args) {
        SpringApplication.run(PglBackApplication.class, args);
    }
}
