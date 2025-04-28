package org.sdia.ebankingbackend;

import org.sdia.ebankingbackend.entities.*;
import org.sdia.ebankingbackend.enums.AccountStatus;
import org.sdia.ebankingbackend.enums.OperationType;
import org.sdia.ebankingbackend.repositories.AccountOperationRepository;
import org.sdia.ebankingbackend.repositories.BankAccountRepository;
import org.sdia.ebankingbackend.repositories.CustomerRepository;
import org.sdia.ebankingbackend.services.BanService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BanService banService) {
        return args -> {
banService.Consulter();
        };
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Hassan", "Yassine", "Yassmine", "Aicha").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });

            customerRepository.findAll().forEach(cust -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random() * 90000);
                currentAccount.setCreationDate(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                savingAccount savingaccount = new savingAccount();
                savingaccount.setId(UUID.randomUUID().toString());
                savingaccount.setBalance(Math.random() * 90000);
                savingaccount.setCreationDate(new Date());
                savingaccount.setStatus(AccountStatus.CREATED);
                savingaccount.setCustomer(cust);
                savingaccount.setInterestRate(5.5);
                bankAccountRepository.save(savingaccount);
            });

            bankAccountRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 5; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random() * 12000);
                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }
}
