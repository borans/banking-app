package com.bank.banking_app;

import com.bank.banking_app.model.Customer;
import com.bank.banking_app.repository.CustomerRepository;
import com.bank.banking_app.model.Account;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BankingAppApplication implements CommandLineRunner {

	private final CustomerRepository customerRepository;

    public BankingAppApplication(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(BankingAppApplication.class, args);
	}

	/**
	 * @Bean
	 * 	    public OpenAPI customOpenAPI(@Value("${application-description}") String description,
	 *                                 @Value("${application-version}") String version){
	 * 		return new OpenAPI()
	 * 				.info(new ProcessHandle.Info()
	 * 						.title("Account API")
	 * 						.version(version)
	 * 						.description(description)
	 * 						.license(new License().name("Account API Licence")));
	 *    }
	 *
	 *    @Bean
	 *    public Clock clock() {
	 * 		return Clock.systemUTC();
	 *    }

	 */


	@Override
	public void run(String... args)  {

		Set<Account> accounts = new HashSet<>();
		Customer customer1 = customerRepository.save(new Customer("", "Boran", "Sezen",
				new HashSet<>()));
		Customer customer2 = customerRepository.save(new Customer("", "Metin", "Sezen",
				new HashSet<>()));

		System.out.println(customer1);
		System.out.println(customer2);



	}




}
