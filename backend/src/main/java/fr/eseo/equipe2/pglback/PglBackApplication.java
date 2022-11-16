package fr.eseo.equipe2.pglback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2 // /swagger-ui/index.html
public class PglBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(PglBackApplication.class, args);
    }

}
