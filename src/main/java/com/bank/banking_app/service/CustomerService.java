package com.bank.banking_app.service;

import com.bank.banking_app.dto.CustomerDTO;
import com.bank.banking_app.dto.converter.CustomerDtoConverter;
import com.bank.banking_app.exception.CustomerNotFoundException;
import com.bank.banking_app.model.Customer;
import com.bank.banking_app.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }


    /*
    Burada protected kullanmamızın sebebi şu: protected sadece aynı packagedaki classlardan ulaşılır. Bu da
    şu anlama geliyor, repodan çektiğim customerı ben mantıken sadece servisler arasında arka tarafta kullanıcam.
    öne bir customer bilgisi taşımıycam. o yüzden -customerı repodan çağırıp
    sadece servisler arasında dolaştıracağım için- bunu protected yapıp encapsulation sağlamış oluruz. bunu gözet.
    public private protectedlarına önem ver.
     */

    protected Customer findCustomerById(String customerId){

        // bu Id de customer yoksa kullanıcıyı uyarmak için exception yazmam lazım ki bilsin. [business rule]
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Could Not Find by Id: " + customerId));
    }

    // customer controllerının customera ulaşması için method. yukarıdaki protected sadece serviceler arasında
    //kullanılabilir.
    public CustomerDTO getCustomerById(String customerId){
        return customerDtoConverter.convert(findCustomerById(customerId));
    }

}
