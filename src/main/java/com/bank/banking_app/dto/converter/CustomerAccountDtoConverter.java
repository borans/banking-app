package com.bank.banking_app.dto.converter;

import com.bank.banking_app.dto.CustomerAccountDTO;
import com.bank.banking_app.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerAccountDtoConverter {

    private final TransactionDtoConverter transactionDtoConverter;

    public CustomerAccountDtoConverter(TransactionDtoConverter converter) {
        this.transactionDtoConverter = converter;
    }

    public CustomerAccountDTO convert(Account from) {
        return new CustomerAccountDTO(
                Objects.requireNonNull(from.getId()),
                from.getBalance(),
                from.getCreationDate(),
                Objects.requireNonNull(from.getTransactions())
                        .stream()
                        .map(transactionDtoConverter::convert)
                        .collect(Collectors.toSet()));
    }
}