package com.theam.api.controller;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    private static final String CUSTOMER_ENDPOINT = "/api/customer";

    @Autowired
    CustomerController customerController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostRequestToCustomerAndValidCustomer_thenCorrectResponse() throws Exception {
        String customer = "{" +
                "\"name\": \"Zelda\"," +
                "\"surname\":\"Orwell\"," +
                "\"deleted\":\"false\"," +
                "\"roles\":[ \"ROLE_ADMIN\"]," +
                "\"createdBy\": {" +
                "\"id\": \"1\"," +
                "\"username\": \"finalboss@example.com\"," +
                "\"password\":\"Asd_1234\"," +
                "\"deleted\":\"false\"," +
                "\"roles\":[ \"ROLE_ADMIN\"]" +
                "}" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER_ENDPOINT)
                .content(customer)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenPostRequestToCustomerAndInvalidName_thenCorrectResponse() throws Exception {
        String customer = "{" +
                "\"surname\":\"Orwell\"," +
                "\"deleted\":\"false\"," +
                "\"roles\":[ \"ROLE_ADMIN\"]," +
                "\"createdBy\": {" +
                "\"id\": \"1\"," +
                "\"username\": \"finalboss@example.com\"," +
                "\"password\":\"Asd_1234\"," +
                "\"deleted\":\"false\"," +
                "\"roles\":[ \"ROLE_ADMIN\"]" +
                "}" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER_ENDPOINT)
                .content(customer)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("A name needs to be defined")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}
