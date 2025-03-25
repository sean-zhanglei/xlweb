package org.nbug.mico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Starter
 *
 * @author Frank Zhang
 */
@SpringBootApplication(scanBasePackages = {"org.nbug.mico", "com.alibaba.cola"})
public class ApplicationMicoServerInfra {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMicoServerInfra.class, args);
    }
}
