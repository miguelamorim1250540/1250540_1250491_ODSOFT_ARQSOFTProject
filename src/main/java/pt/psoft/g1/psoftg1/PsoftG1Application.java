package pt.psoft.g1.psoftg1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "pt.psoft.g1.psoftg1")
@EnableJpaRepositories(basePackages = "pt.psoft.g1.psoftg1")
@EntityScan(basePackages = "pt.psoft.g1.psoftg1")
public class PsoftG1Application {

    public static void main(String[] args) {
        SpringApplication.run(PsoftG1Application.class, args);
    }
}
