package com.example.marimo_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class MarimoBackApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MarimoBackApplication.class, args);
    }

}
