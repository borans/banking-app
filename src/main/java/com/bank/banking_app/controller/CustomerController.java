package com.bank.banking_app.controller;

import com.bank.banking_app.dto.CustomerDTO;
import com.bank.banking_app.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // @PathVariabla urlden bilgi alır @RequestBody jsondan bilgi alır.
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@Valid @PathVariable String customerId){
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

}
