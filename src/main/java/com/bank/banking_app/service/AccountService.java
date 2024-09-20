package com.bank.banking_app.service;

import com.bank.banking_app.dto.converter.AccountDtoConverter;
import com.bank.banking_app.model.Customer;
import com.bank.banking_app.model.Transaction;
import com.bank.banking_app.repository.AccountRepository;
import com.bank.banking_app.dto.AccountDTO;
import com.bank.banking_app.dto.CreateAccountRequest;
import com.bank.banking_app.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;


    public AccountService(AccountRepository accountRepository, CustomerService customerService,
                          AccountDtoConverter accountDtoConverter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDtoConverter = accountDtoConverter;
    }

    public AccountDTO createAccount(CreateAccountRequest createAccountRequest){
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now(),
                customer);

        if(createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0){
            // elde bir initial credit trx olduğu için ekstra olarak bu ifin içinde
            // yeni bir transaction yaratıp account nesnemizin transaction setine bunu ekledik.

            Transaction transaction =  new Transaction(createAccountRequest.getInitialCredit(), account);

            account.getTransactions().add(transaction);
        }

        // burası bizim endpointimiz. bu servisle oluşturduğumuz account objesini usera vermek istiyoruz. o yüzden
        //dtoya çevirip veriyoruz. dto bir ara katman görevi görecek dışarıyla ilişkide. doğrudan modeli göstermiycez.

        // aynı satırda aynı zamanda yeni create edilen accountu accountun reposuna da save ediyoruz.

        return accountDtoConverter.convert(accountRepository.save(account));

    }


}
