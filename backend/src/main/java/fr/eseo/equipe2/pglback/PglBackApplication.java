package fr.eseo.equipe2.pglback;

import fr.eseo.equipe2.pglback.consumeApi.IrigoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Objects;

@SpringBootApplication
@EnableSwagger2 // /swagger-ui/index.html
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PglBackApplication extends SpringBootServletInitializer {
    @Autowired
    IrigoApi irigoApi;

    @Value("${spring.profiles.active:Unknown}")
    private String activeProfile;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PglBackApplication.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        if (activeProfile.equals("prod")) {
            irigoApi.fetchAllStop();
            irigoApi.fetchBusLines();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(PglBackApplication.class, args);
    }
}
