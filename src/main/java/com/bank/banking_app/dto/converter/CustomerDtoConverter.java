package com.bank.banking_app.dto.converter;

import com.bank.banking_app.dto.AccountCustomerDTO;
import com.bank.banking_app.dto.CustomerDTO;
import com.bank.banking_app.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {

    private final CustomerAccountDtoConverter customerAccountDTOConverter;

    public CustomerDtoConverter(CustomerAccountDtoConverter customerAccountDTOConverter) {
        this.customerAccountDTOConverter = customerAccountDTOConverter;
    }


    public AccountCustomerDTO convertToAccountCustomerDTO(Customer from){
        if(from == null){
            return new AccountCustomerDTO("", "", "");
        }
        return new AccountCustomerDTO(from.getId(), from.getName(), from.getSurname());
    }


    public CustomerDTO convert(Customer from){
        return new CustomerDTO(from.getId(),
                from.getName(),
                from.getSurname(),
                Objects.requireNonNull(from.getAccounts())
                        .stream()
                        .map(customerAccountDTOConverter::convert).collect(Collectors.toSet())
                );
    }

}
