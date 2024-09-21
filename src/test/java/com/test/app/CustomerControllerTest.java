package com.test.app;

import com.test.app.config.JwtAuthConverterProperties;
import com.test.app.config.WebSecurityConfig;
import com.test.app.controller.CustomerController;
import com.test.app.domain.Customer;
import com.test.app.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(CustomerController.class)
@ComponentScan(basePackages = "com.test.app")
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;


    @Test
    @WithMockUser(username = "e.elmokhtar",roles = {"user"})
    void shouldReturnAllCustomers() throws Exception {
        when(customerRepository.findAll())
                .thenReturn(List.of(new Customer(1,"test")));

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/test/user"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


}

