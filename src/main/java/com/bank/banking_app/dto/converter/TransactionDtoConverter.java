package com.bank.banking_app.dto.converter;

import com.bank.banking_app.dto.TransactionDTO;
import com.bank.banking_app.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoConverter {

    public TransactionDTO convert(Transaction from){
        return new TransactionDTO(from.getId(), from.getTransactionType(), from.getAmount(),
                from.getTransactionDate());
    }

}
