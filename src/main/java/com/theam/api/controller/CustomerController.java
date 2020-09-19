package com.theam.api.controller;

import com.theam.api.converter.Customerconverter;
import com.theam.api.dto.CustomerDto;
import com.theam.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private Customerconverter customerconverter;

    @GetMapping
    public List<CustomerDto> findAll() {
        return customerconverter.convertFromEntityCollection(customerService.findAll());
    }

    @GetMapping("{id}")
    public CustomerDto findCustomer(@PathVariable long id) {
        return customerconverter.convertFromEntity(customerService.findById(id));
    }

    @PostMapping
    public CustomerDto save(@RequestBody CustomerDto customerDto) {
        return customerconverter.convertFromEntity(customerService.save(customerDto));
    }

    @PutMapping("/{customerId}")
    public CustomerDto update(@PathVariable long customerId, @RequestBody CustomerDto customerDto) {
        return customerconverter.convertFromEntity(customerService.update(customerId, customerDto));
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable long customerId) {
        customerService.deleteById(customerId);
    }
}
