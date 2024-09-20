package com.bank.banking_app.controller;

import com.bank.banking_app.dto.AccountDTO;
import com.bank.banking_app.dto.CreateAccountRequest;
import com.bank.banking_app.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    // POST mapping dbye veri yükler PUT mapping dbdeki veriyi update eder. Dönüş kodları da farklıdır.(201/203)
    /*
    @RequestBody datayı requestteki jsondan çekmesini söylemek için bir annotation.
    @Valid gelen bilgilerin valid olup olmadığını beliritiyor dependincelere bir spring valid kütüphanesi ekledik.
    ayrıca CreateAccountRequestte requestin fieldlarının hangi koşullarda valid olacağıyla ilgili iki field
    annotationu var. Eğer fieldlar valid değilse GeneralExceptionHandlerda yazdığımız handleMethodArgumentNotValid
    exception handlerı devreye girip kullanıcıya hata mesajlı paketli güzel bir bad request gönderecek. Her şey handle
    edildi böylece.
     */
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody CreateAccountRequest accountCreationRequest){
        return ResponseEntity.ok(accountService.createAccount(accountCreationRequest));
    }
}
