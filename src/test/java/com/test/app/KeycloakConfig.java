package com.test.app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.keycloak.credentials")
public class KeycloakConfig {

    private String clientId;

    private String clientSecret;

    private String realm;

    private String username;

    private String password;

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRealm() {
        return realm;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
// Getters and other methods
}
