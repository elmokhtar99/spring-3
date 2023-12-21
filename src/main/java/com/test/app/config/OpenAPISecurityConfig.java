package com.test.app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPISecurityConfig {


    private static final String OAUTH_SCHEME_NAME = "my_oAuth_security_schema";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME))
                .info(new Info().title("Todos Management Service")
                        .description("A service providing todos.")
                        .version("1.0"));
    }

//    private SecurityScheme createOAuthScheme() {
//        OAuthFlows flows = createOAuthFlows();
//        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
//                .flows(flows);
//    }
private SecurityScheme createOAuthScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(createOAuthFlows());
}
    private OAuthFlows createOAuthFlows() {
        OAuthFlow flow = createAuthorizationCodeFlow();
        return new OAuthFlows().implicit(flow);
    }

    private OAuthFlow createAuthorizationCodeFlow() {
        return new OAuthFlow()
                .authorizationUrl("http://localhost:8443" + "/realms/" + "learn-realm" + "/protocol/openid-connect/auth");
    }

}