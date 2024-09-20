package com.bank.banking_app.dto.converter;

import com.bank.banking_app.dto.AccountDTO;
import com.bank.banking_app.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AccountDtoConverter {

    private final CustomerDtoConverter customerDtoConverter;
    private final TransactionDtoConverter transactionDtoConverter;


    public AccountDtoConverter(CustomerDtoConverter customerDtoConverter,
                               TransactionDtoConverter transactionDtoConverter) {
        this.customerDtoConverter = customerDtoConverter;
        this.transactionDtoConverter = transactionDtoConverter;
    }




    public AccountDTO convert (Account from){
        return new AccountDTO(from.getId(),
                from.getCreationDate(),
                from.getBalance(),
                customerDtoConverter.convertToAccountCustomerDTO(from.getCustomer()),
                Objects.requireNonNull(from.getTransactions())
                        .stream()
                        .map(transactionDtoConverter::convert).collect(Collectors.toSet()));
    }

}
