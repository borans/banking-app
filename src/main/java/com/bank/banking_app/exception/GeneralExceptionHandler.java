package com.bank.banking_app.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    // RestControllerAdvicelar sizin throw ettiğiniz bütün exceptionları yakalayıp bir HTTPS response üretiyor.

    /*
    RestControllerAdvice diye bir generalexceptionhandler olmadığı senaryoda olan şey bir exception fırlatıldığında
    service controllera hatayı bildirir controller da bu hatayla ilgili bir işlem yapacaksa yapar ve exception handle
    edilir.

    RestControllerAdvisorun yaptığı şeyse service controllera hatayı bildirirken araya girip akışı kesiyor ve exceptionu
    alıyor ve kendi içinde (bu kodda) bir https response üretiyor sonra direkt usera veriyor exceptionu controllerı
    araya katmadan.
     */
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> customerNotFoundExceptionHandler(CustomerNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    /*
    KULLANICI aldığı hatanın tam olarak nasıl handle edildiğini görmesini sağlıyor. Argument not valid ne demek
    sen bu api a bir request gönderdiğinde requestteki iniialCredit veya customerId valid değilse
    (intiial credit negatif mesela) bu hatayı paketleyip response olarak yolla kullanıcıya.
     */

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }



}
