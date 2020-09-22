package com.theam.api.service.impl;

import com.theam.api.converter.CustomerConverter;
import com.theam.api.dao.CustomerDao;
import com.theam.api.dto.CustomerDto;
import com.theam.api.exception.NotFoundException;
import com.theam.api.model.Customer;
import com.theam.api.service.AuthenticationService;
import com.theam.api.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    private final CustomerConverter customerconverter;

    private final AuthenticationService authenticationService;

    public CustomerServiceImpl(CustomerDao customerDao, CustomerConverter customerconverter, AuthenticationService authenticationService) {
        this.customerDao = customerDao;
        this.customerconverter = customerconverter;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public Customer findById(long id) {
        return customerDao.findById(id).orElseThrow(()-> new NotFoundException("Customer not found"));
    }

    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = customerconverter.convertFromDto(customerDto);
        customer.setCreatedBy(authenticationService.getLoggedUser());
        return customerDao.save(customer);
    }

    @Override
    public Customer update(Long id, CustomerDto customerDto) {
        customerDao.findById(customerDto.getId())
                .orElseThrow(()-> new NotFoundException("Customer not found"));
        Customer customerToUpdate = customerconverter.convertFromDto(customerDto);
        customerToUpdate.setModifiedBy(authenticationService.getLoggedUser());
        return customerDao.save(customerToUpdate);
    }

    @Override
    public void deleteById(Long customerId) {
        Customer customerToDelete = customerDao.findById(customerId)
                .orElseThrow(()-> new NotFoundException("Customer not found"));
        customerToDelete.setDeleted(true);
        customerDao.save(customerToDelete);
    }
}
