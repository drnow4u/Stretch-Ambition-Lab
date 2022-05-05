package com.gitlab.marnow.stretchambitionlab.springpostgresbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringPostgresBaseApplication {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println("CMD arg: " + arg);
        }
        context = SpringApplication.run(SpringPostgresBaseApplication.class, args);
    }

}
