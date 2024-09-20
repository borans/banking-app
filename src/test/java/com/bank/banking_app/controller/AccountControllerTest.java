package com.bank.banking_app.controller;

import com.bank.banking_app.dto.CreateAccountRequest;
import com.bank.banking_app.dto.converter.AccountDtoConverter;
import com.bank.banking_app.model.Customer;
import com.bank.banking_app.repository.AccountRepository;
import com.bank.banking_app.repository.CustomerRepository;
import com.bank.banking_app.service.AccountService;
import com.bank.banking_app.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.Clock;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/*
integration testin unit testten farkı servicelerde unit test kullanılıyor. controllerlarda integration test yazılır.
controllerın servisi çağırması beklenir ve gerçekten datayı databasee kaydedip kaydedilmediği yani bütün süreç
kontrol ediliyor. integration testin amacı budur.


A unit test is a test written by the programmer to verify that a relatively small piece of code is doing what
it is intended to do. They are narrow in scope, they should be easy to write and execute, and their effectiveness
depends on what the programmer considers to be useful. The tests are intended for the use of the programmer,
they are not directly useful to anybody else, though, if they do their job, testers and users downstream should
benefit from seeing fewer bugs.

Part of being a unit test is the implication that things outside the code under test are mocked or stubbed out.
 Unit tests shouldn't have dependencies on outside systems. They test internal consistency as opposed to proving
 that they play nicely with some outside system.

An integration test is done to demonstrate that different pieces of the system work together. Integration tests
can cover whole applications, and they require much more effort to put together. They usually require resources
like database instances and hardware to be allocated for them. The integration tests do a more convincing job of
demonstrating the system works (especially to non-programmers) than a set of unit tests can, at least to the extent
 the integration test environment resembles production.
 */
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "server-port=0",
        "command.line.runner.enabled=false"
})
@RunWith(SpringRunner.class)
@DirtiesContext
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Clock clock;

    @MockBean
    private Supplier<UUID> uuidSupplier;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountDtoConverter accountDtoConverter;

    private AccountService accountService = new AccountService(accountRepository, customerService,
            accountDtoConverter);
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void testCreateAccount_whenCustomerIdExists_shouldCreateAccountAndReturnAccountDto() throws Exception {
        Customer customer = customerRepository.save(new Customer("Diyar", "Sezen"));
        CreateAccountRequest request = new CreateAccountRequest(Objects.requireNonNull(customer.getId()), new BigDecimal(100));

        this.mockMvc.perform(post("/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.balance", is(100)))
                .andExpect(jsonPath("$.customer.id", is(customer.getId())))
                .andExpect(jsonPath("$.customer.name", is("Diyar")))
                .andExpect(jsonPath("$customer.surname", is("Sezen")))
                .andExpect(jsonPath("$.transactions", hasSize(1)));

    }


}