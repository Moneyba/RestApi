package com.theam.api.service.impl;

import com.theam.api.dao.CustomerDao;
import com.theam.api.exception.NotFoundException;
import com.theam.api.model.Customer;
import com.theam.api.service.AuthenticationService;
import com.theam.api.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerDao customerDao;

    private final AuthenticationService authenticationService;

    public CustomerServiceImpl(CustomerDao customerDao, AuthenticationService authenticationService) {
        this.customerDao = customerDao;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerDao.findById(id).orElseThrow(()-> new NotFoundException("Customer not found"));
    }

    @Override
    public Customer save(Customer customer) {
        log.info("Trying to save customer with id " + customer.getId());
        customer.setCreatedBy(authenticationService.getLoggedUser());
        return customerDao.save(customer);
    }

    @Override
    public Customer update(Long id, Customer customer) {
        log.info("Trying to update customer with id " + customer.getId());
        customerDao.findById(customer.getId())
                .orElseThrow(()-> new NotFoundException("Customer not found"));
        customer.setModifiedBy(authenticationService.getLoggedUser());
        return customerDao.save(customer);
    }

    @Override
    public void deleteById(Long customerId) {
        log.info("Trying to delete user with id " + customerId);
        Customer customerToDelete = customerDao.findById(customerId)
                .orElseThrow(()-> new NotFoundException("Customer not found"));
        customerToDelete.setDeleted(true);
        customerDao.save(customerToDelete);
    }
}
