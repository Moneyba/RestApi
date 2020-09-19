package com.theam.api.service;

import com.theam.api.dto.CustomerDto;
import com.theam.api.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    Customer findById(long id);
    Customer save(CustomerDto customerDto);
    Customer update(Long id, CustomerDto customerDto);
    void deleteById(Long customerId);
}
