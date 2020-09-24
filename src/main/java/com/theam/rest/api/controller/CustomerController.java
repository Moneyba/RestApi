package com.theam.rest.api.controller;

import com.theam.rest.api.converter.CustomerConverter;
import com.theam.rest.api.dto.CustomerDto;
import com.theam.rest.api.model.Customer;
import com.theam.rest.api.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
    private final CustomerService customerService;

    private final CustomerConverter customerconverter;

    public CustomerController(CustomerService customerService, CustomerConverter customerconverter) {
        this.customerService = customerService;
        this.customerconverter = customerconverter;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDto> findAll() {
        return customerconverter.convertFromEntityCollection(customerService.findAll());
    }

    @GetMapping(path = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto findCustomer(@PathVariable Long customerId) {
        return customerconverter.convertFromEntity(customerService.findById(customerId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto save(@Valid @RequestBody CustomerDto customerDto) {
        Customer customer = customerconverter.convertFromDto(customerDto);
        return customerconverter.convertFromEntity(customerService.save(customer));
    }

    @PutMapping(path = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto update(@PathVariable Long customerId, @Valid @RequestBody CustomerDto customerDto) {
        Customer customer = customerconverter.convertFromDto(customerDto);
        return customerconverter.convertFromEntity(customerService.update(customerId, customer));
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable Long customerId) {
        customerService.deleteById(customerId);
    }

}
