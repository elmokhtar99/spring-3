package com.test.app;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Test {
    @Value("${app.keycloak.credentials.username}")
    private String someProperty;

    @PostConstruct
    public void init() {
        System.out.println("Loaded some.property.in.test: " + someProperty);
    }
}
