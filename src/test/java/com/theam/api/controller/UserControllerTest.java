package com.theam.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theam.api.converter.UserConverter;
import com.theam.api.dto.UserDto;
import com.theam.api.model.User;
import com.theam.api.service.UserService;
import com.theam.api.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.theam.api.controller.ResponseBodyMatchers.responseBody;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    private static final String USER_ENDPOINT = "/api/user";

    @MockBean
    private UserConverter userConverter;

    @MockBean
    private UserService userService;

    @Qualifier("userDetailsServiceImpl")
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @WithMockUser(username="mockedUser", roles={"ADMIN"})
    public void whenUserRequestTheUsers_thenTheUserListIsReturned() throws Exception{
        UserDto user1 = new UserDto(1L, "admin@theam.com", Collections.singletonList("ROLE_ADMIN"), "***");
        UserDto user2 = new UserDto(2L, "user@theam.com", Collections.singletonList("ROLE_USER"), "***");
        List<UserDto> users = Arrays.asList(user1, user2);
        when(userService.findAll()).thenReturn(new ArrayList<>());
        when(userConverter.convertFromEntityCollection(anyCollection())).thenReturn(users);
        mockMvc.perform(MockMvcRequestBuilders.get(USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(users));
    }

    @Test
    @WithMockUser(username="mockedUser", roles={"ADMIN"})
    public void whenCreateUser_thenTheUserIsReturned() throws Exception {
        UserDto userDto = new UserDto(null,  "user@theam.com", Collections.singletonList("ROLE_USER"), null);
        when(userService.save(any(UserDto.class))).thenReturn(new User());
        when(userConverter.convertFromEntity(any(User.class))).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.post(USER_ENDPOINT)
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(userDto));
    }




    @Test
    @WithMockUser(username="mockedUser", roles={"ADMIN"})
    public void whenUpdateUser_thenTheUpdatedUserIsReturned() throws Exception {
        UserDto userDto = new UserDto(1L, "user@theam.com", Collections.singletonList("ROLE_USER"), null);
        when(userService.update(anyLong(), any(UserDto.class))).thenReturn(new User());
        when(userConverter.convertFromEntity(any(User.class))).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.put(USER_ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(userDto));
    }

    @Test
    @WithMockUser(username="mockedUser", roles={"ADMIN"})
    public void whenUserDeleteCustomer_thenReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(USER_ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="mockedUser", roles={"ADMIN"})
    public void whenChangeAdminStatus_thenReturns200() throws Exception {
        UserDto userDto = new UserDto(3L, "newAdmin@theam.com", Collections.singletonList("ROLE_ADMIN"), null);
        when(userService.update(anyLong(), any(UserDto.class))).thenReturn(new User());
        when(userConverter.convertFromEntity(any(User.class))).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.put(USER_ENDPOINT + "/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username="mockedUser", roles={"ADMIN"})
    public void whenValidInput_thenReturns200() throws Exception {
        UserDto userDto = new UserDto(null, "admin@theam.com", Collections.singletonList("ROLE_ADMIN"), "***");
        mockMvc.perform(MockMvcRequestBuilders.post(USER_ENDPOINT)
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username="mockedUser", roles={"ADMIN"})
    public void whenNullUsername_thenReturns400() throws Exception {
        UserDto userDto = new UserDto(null, null, Collections.singletonList("ROLE_ADMIN"), "***");
        mockMvc.perform(MockMvcRequestBuilders.post(USER_ENDPOINT)
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username="mockedUser", roles={"ADMIN"})
    public void whenValidInput_thenMapsToBusinessModel() throws Exception {
        UserDto userDto = new UserDto(null, "admin@theam.com", Collections.singletonList("ROLE_ADMIN"), "***");
        mockMvc.perform(MockMvcRequestBuilders.post(USER_ENDPOINT)
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<UserDto> userCaptor = ArgumentCaptor.forClass(UserDto.class);
        verify(userService, times(1)).save(userCaptor.capture());
        assertThat(userCaptor.getValue().getUsername()).isEqualTo("admin@theam.com");
        assertThat(userCaptor.getValue().getPassword()).isEqualTo("***");
        assertThat(userCaptor.getValue().getRoles()).isEqualTo(Collections.singletonList("ROLE_ADMIN"));

    }

    @Test
    @WithMockUser(username="mockedUser", roles={"ADMIN"})
    void whenNullUsername_thenReturns400AndErrorResult() throws Exception {
        UserDto userDto = new UserDto(null, null, Collections.singletonList("ROLE_ADMIN"), "*****");
        mockMvc.perform(MockMvcRequestBuilders.post(USER_ENDPOINT)
          .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto)))
          .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsError("username", "A username needs to be defined"));
    }

}
