package com.theam.rest.api.service.impl;

import com.theam.rest.api.dao.CustomerDao;
import com.theam.rest.api.exception.NotFoundException;
import com.theam.rest.api.model.Customer;
import com.theam.rest.api.model.User;
import com.theam.rest.api.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerDao customerDao;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private final String name = "name";

    @Test
    void whenFindAll_shouldCallCustomerDaoFindAll() {
        customerService.findAll();
        verify(customerDao).findAll();
    }

    @Test
    void whenTheCustomerIsLoadedById_thenTheUserIsReturned() {
        Long userId = 1L;
        when(customerDao.findById(anyLong())).thenReturn(Optional.of(new Customer()));
        customerService.findById(userId);
        verify(customerDao).findById(userId);
    }

    @Test
    void whenTheCustomerIsNotFound_thenThereIsANotFoundException() {
        when(customerDao.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> customerService.findById(1L));
    }

    @Test
    void whenANewCustomerIsCreatedSuccessfully_thenTheCustomerIsReturned() {
        Customer expectedCustomer = new Customer();
        expectedCustomer.setName(name);
        expectedCustomer.setSurname("surname");
        when(authenticationService.getLoggedUser()).thenReturn(new User());
        when(customerDao.save(any(Customer.class))).then(returnsFirstArg());
        customerService.save(expectedCustomer);
        verify(customerDao).save(expectedCustomer);
    }

    @Test
    void whenACustomerIsUpdatedSuccessfully_thenTheCustomerIsReturned() {
        Long customerId = 1L;
        Customer customer = new Customer(customerId, name, "surname", "photoURL", false,  new User(), null);
        when(customerDao.findById(anyLong())).thenReturn(Optional.of(customer));
        when(authenticationService.getLoggedUser()).thenReturn(new User());
        when(customerDao.save(any(Customer.class))).thenReturn(customer);
        customerService.update(customerId, customer);
        verify(customerDao).save(customer);
    }

}