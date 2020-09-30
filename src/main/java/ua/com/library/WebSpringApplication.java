package ua.com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class WebSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSpringApplication.class, args);
    }

}
