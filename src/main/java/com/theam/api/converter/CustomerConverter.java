package com.theam.api.converter;

import com.theam.api.dto.CustomerDto;
import com.theam.api.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter extends GenericConverter<Customer, CustomerDto> {

    final UserConverter userConverter;

    public CustomerConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

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
        if (dto.getModifiedBy() != null) {
            customer.setModifiedBy(userConverter.convertFromDto(dto.getModifiedBy()));
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
        customerDto.setCreatedBy(userConverter.convertFromEntity(customer.getCreatedBy()));
        if (customer.getModifiedBy() != null) {
            customerDto.setModifiedBy(userConverter.convertFromEntity(customer.getModifiedBy()));
        }
        return customerDto;
    }
}
