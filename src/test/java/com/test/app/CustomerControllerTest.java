package com.test.app;

import com.test.app.config.JwtAuthConverterProperties;
import com.test.app.controller.CustomerController;
import com.test.app.domain.Customer;
import com.test.app.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
@Import({JwtAuthConverterProperties.class, KeycloakConfig.class})
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private KeycloakConfig keycloakConfig;

    @Test
    void shouldReturnAllCustomers() throws Exception {
        String token = obtainAccessToken();
        when(customerRepository.findAll())
                .thenReturn(List.of(new Customer(1,"test")));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/test/admin")
                        .header("Authorization","Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    public String obtainAccessToken() {

        String tokenUrl = "http://localhost:8443/realms/" + keycloakConfig.getRealm() + "/protocol/openid-connect/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", keycloakConfig.getClientId());
        params.add("client_secret", keycloakConfig.getClientSecret());
        params.add("username", keycloakConfig.getUsername());
        params.add("password", keycloakConfig.getPassword());
        params.add("grant_type", "password");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, params, Map.class);
        return (String) response.getBody().get("access_token");
    }

}

