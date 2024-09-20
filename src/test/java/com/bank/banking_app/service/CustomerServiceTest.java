package com.bank.banking_app.service;

import com.bank.banking_app.dto.CustomerDTO;
import com.bank.banking_app.dto.converter.CustomerDtoConverter;
import com.bank.banking_app.exception.CustomerNotFoundException;
import com.bank.banking_app.model.Customer;
import com.bank.banking_app.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private CustomerDtoConverter customerDtoConverter;

    @BeforeEach
    public void setUp() {

        customerRepository = mock(CustomerRepository.class);
        customerDtoConverter = mock(CustomerDtoConverter.class);
        customerService = new CustomerService(customerRepository, customerDtoConverter);
    }

    @Test
    void testFindByCustomerId_whenCustomerIdExists_shouldReturnCustomer() {
        Customer customer = new Customer("id", "name", "surname", Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        Customer result = customerService.findCustomerById("id");

        assertEquals(result, customer);
    }

    @Test
    void testFindByCustomerId_whenCustomerIdNotExists_shouldReturnCustomerNotFoundException() {

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.findCustomerById("id"));
    }

    // private methodların testi yazılmaz ara bilgi
    @Test
    void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomer() {
        Customer customer = new Customer("id", "name", "surname", Set.of());
        CustomerDTO customerDTO = new CustomerDTO("id", "name", "surname", Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));
        Mockito.when(customerDtoConverter.convert(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.getCustomerById("id");

        assertEquals(result, customerDTO);
    }

    @Test
    void testGetCustomerById_whenCustomerIdNotExists_shouldReturnCustomerNotFoundException() {
        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById("id"));

        // customerDTOconverterın hiçbir methodu çağrılmaz customer olmadığı için hata üretir bunu verify ediyorsun
        // burada.
        Mockito.verifyNoInteractions(customerDtoConverter);
    }


}