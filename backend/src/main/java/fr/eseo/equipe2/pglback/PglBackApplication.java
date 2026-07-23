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
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableMethodSecurity()
public class PglBackApplication extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(PglBackApplication.class);

    @Autowired
    IrigoApi irigoApi;

    @Value("${spring.profiles.active:Unknown}")
    private String activeProfile;

    public static void main(String[] args) {
        SpringApplication.run(PglBackApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PglBackApplication.class);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        if (activeProfile.equals("prod")) {
            try {
                irigoApi.fetchAllStop();
                irigoApi.fetchBusLines();
            } catch (Exception e) {
                logger.error("Startup data initialization failed, application will continue without it", e);
            }
        }
    }
}
