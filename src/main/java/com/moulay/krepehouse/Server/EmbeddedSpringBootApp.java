package com.moulay.krepehouse.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class EmbeddedSpringBootApp {
    private static ConfigurableApplicationContext springContext;

    public static void startServer() {
        // Start Spring Boot in a separate thread
        new Thread(() -> {
            springContext = SpringApplication.run(EmbeddedSpringBootApp.class);
        }).start();
    }

    public static void stopServer() {
        if (springContext != null) {
            springContext.close();
        }
    }

/*    @RestController
    public static class MyController {
        @GetMapping("/api/hello")
        public String hello() {
            return "Hello from Spring Boot inside JavaFX!";
        }
    }*/
}
