package com.theam.api.converter;

import com.theam.api.dto.CustomerDto;
import com.theam.api.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Customerconverter extends GenericConverter<Customer, CustomerDto> {

    @Autowired UserConverter userConverter;

    @Override
    public Customer convertFromDto(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setSurname(dto.getSurname());
        customer.setPhotoUrl(dto.getPhotoUrl());
        if (dto.getCreatedBy() != null) {
            customer.setCreatedBy(userConverter.convertFromDto(dto.getCreatedBy()));
        }
        return customer;
    }

    @Override
    public CustomerDto convertFromEntity(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setSurname(customer.getSurname());
        customerDto.setPhotoUrl(customer.getPhotoUrl());
        if (customer.getCreatedBy() != null) {
            customerDto.setCreatedBy(userConverter.convertFromEntity(customer.getCreatedBy()));
        }
        return customerDto;
    }
}
