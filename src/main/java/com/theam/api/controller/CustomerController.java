package com.theam.api.controller;

import com.theam.api.converter.CustomerConverter;
import com.theam.api.dto.CustomerDto;
import com.theam.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public CustomerDto findCustomer(@PathVariable long customerId) {
        return customerconverter.convertFromEntity(customerService.findById(customerId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto save(@Valid @RequestBody CustomerDto customerDto) {
        return customerconverter.convertFromEntity(customerService.save(customerDto));
    }

    @PutMapping(path = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto update(@PathVariable long customerId, @Valid @RequestBody CustomerDto customerDto) {
        return customerconverter.convertFromEntity(customerService.update(customerId, customerDto));
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable long customerId) {
        customerService.deleteById(customerId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
