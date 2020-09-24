package com.theam.rest.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theam.rest.api.converter.CustomerConverter;
import com.theam.rest.api.dto.CustomerDto;
import com.theam.rest.api.dto.UserDto;
import com.theam.rest.api.model.Customer;
import com.theam.rest.api.service.CustomerService;
import com.theam.rest.api.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {
    private static final String CUSTOMER_ENDPOINT = "/api/customer";

    @MockBean
    private CustomerConverter customerConverter;

    @MockBean
    private CustomerService customerService;

    @Qualifier("userDetailsServiceImpl")
    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(username="mockedUser")
    void whenUserRequestTheCustomers_thenTheCustomerListIsReturned() throws Exception{
        UserDto userDto = new UserDto(1L, "admin@theam.com", Collections.singletonList("ROLE_ADMIN"), null);
        CustomerDto customer1 = new CustomerDto(1L,"Cesar", "Manrique", null, userDto,null);
        CustomerDto customer2 = new CustomerDto(2L,"Salvador", "Dali", null, userDto,null);
        List<CustomerDto> customers = Arrays.asList(customer1, customer2);
        when(customerService.findAll()).thenReturn(new ArrayList<>());
        when(customerConverter.convertFromEntityCollection(anyCollection())).thenReturn(customers);
        mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(customers));
    }

    @Test
    @WithMockUser(username="mockedUser")
    void whenUserRequestCustomerInformation_thenTheCustomerIsReturned() throws Exception {
        String photoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQL59nSXkNhqkVAzpGeRdRbFre1-JEBbQhaCA&usqp=CAU";
        UserDto userDto = new UserDto(1L, "admin@theam.com", Collections.singletonList("ROLE_ADMIN"), null);
        CustomerDto customerDto = new CustomerDto(1L,"Zelda", "Orwell", photoUrl, userDto,null);
        when(customerService.findById(anyLong())).thenReturn(new Customer());
        when(customerConverter.convertFromEntity(any(Customer.class))).thenReturn(customerDto);
        mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(customerDto));
    }

    @Test
    @WithMockUser(username="mockedUser")
    void whenUserCreateCustomer_thenTheCustomerIsReturned() throws Exception {
        UserDto userDto = new UserDto(1L, "admin@theam.com", Collections.singletonList("ROLE_ADMIN"), null);
        CustomerDto customerDto = new CustomerDto(1L,"Zelda", "Orwell", null, userDto,null);
        when(customerConverter.convertFromDto(any(CustomerDto.class))).thenReturn(new Customer());
        when(customerService.save(any(Customer.class))).thenReturn(new Customer());
        when(customerConverter.convertFromEntity(any(Customer.class))).thenReturn(customerDto);
        mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(customerDto));
    }

    @Test
    @WithMockUser(username="mockedUser")
    void whenUserUpdateCustomer_thenTheUpdatedCustomerIsReturned() throws Exception {
        UserDto userDto = new UserDto(1L, "admin@theam.com", Collections.singletonList("ROLE_ADMIN"), null);
        CustomerDto customerDto = new CustomerDto(1L,"Zelda", "Orwell", null, userDto,null);
        when(customerConverter.convertFromDto(any(CustomerDto.class))).thenReturn(new Customer());
        when(customerService.update(anyLong(), any(Customer.class))).thenReturn(new Customer());
        when(customerConverter.convertFromEntity(any(Customer.class))).thenReturn(customerDto);
        mockMvc.perform(MockMvcRequestBuilders.put(CUSTOMER_ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customerDto)))
                .andExpect(status().isOk())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(customerDto));
    }

    @Test
    @WithMockUser(username="mockedUser")
    void whenUserDeleteCustomer_thenReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(CUSTOMER_ENDPOINT + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="mockedUser")
    void whenNullCustomerName_thenReturns400() throws Exception {
        UserDto userDto = new UserDto(1L, "admin@theam.com", Collections.singletonList("ROLE_ADMIN"), null);
        CustomerDto customerDto = new CustomerDto(1L,null, "Orwell", null, userDto,null);
        mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER_ENDPOINT)
                .content(objectMapper.writeValueAsString(customerDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
