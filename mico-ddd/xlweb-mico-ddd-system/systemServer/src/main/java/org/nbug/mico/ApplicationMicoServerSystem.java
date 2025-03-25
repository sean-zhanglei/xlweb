package org.nbug.mico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Starter
 *
 * @author Frank Zhang
 */
@SpringBootApplication(scanBasePackages = {"org.nbug.mico", "com.alibaba.cola"})
public class ApplicationMicoServerSystem {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMicoServerSystem.class, args);
    }
}
