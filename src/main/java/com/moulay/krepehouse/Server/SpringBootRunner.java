package com.moulay.krepehouse.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringBootRunner implements Runnable{

    private Class<?> applicationClass;
    private ConfigurableApplicationContext context;
    private String[] args;

    public SpringBootRunner(Class<?> applicationClass, String[] args) {
        this.applicationClass = applicationClass;
        this.args = args;
    }

    @Override
    public void run() {
        this.context = SpringApplication.run(applicationClass, args);
    }

    public void shutdown() {
        if (context != null) {
            context.close();
        }
    }
}
