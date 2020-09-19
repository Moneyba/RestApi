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
public class UserControllerTest {
    private static final String USER_ENDPOINT = "/api/user";

    @Autowired
    UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostRequestToUsersAndValidUser_thenCorrectResponse() throws Exception {
        String user = "{" +
                "\"username\": \"finalboss@example.com\"," +
                "\"password\":\"Asd_1234\"," +
                "\"deleted\":\"false\"," +
                "\"roles\":[ \"ROLE_ADMIN\"]" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post(USER_ENDPOINT)
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenPostRequestToUserAndInvalidUsername_thenCorrectResponse() throws Exception {
        String user = "{" +
                "\"password\":\"Asd_1234\"," +
                "\"deleted\":\"false\"," +
                "\"roles\":[ \"ROLE_ADMIN\"]" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post(USER_ENDPOINT)
                .content(user)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Is.is("A username needs to be defined")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}
