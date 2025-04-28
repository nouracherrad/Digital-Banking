package org.sdia.ebankingbackend.services;

import org.sdia.ebankingbackend.entities.BankAccount;
import org.sdia.ebankingbackend.entities.CurrentAccount;
import org.sdia.ebankingbackend.entities.savingAccount;
import org.sdia.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class BanService {
    public BankAccountRepository bankAccountRepository;
    public void Consulter(){
        BankAccount bankAccount = bankAccountRepository.findById("4cde4001-5625-4186-867a-f3e38fb1a2d6").orElse(null);
        if (bankAccount != null) {
            System.out.println("************");
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getCreationDate());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getClass().getSimpleName());

            if (bankAccount instanceof CurrentAccount) {
                System.out.println("OverDraft: " + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof savingAccount) {
                System.out.println("InterestRate: " + ((savingAccount) bankAccount).getInterestRate());
            }

            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println("************");
                System.out.println(op.getId());
                System.out.println(op.getOperationDate());
                System.out.println(op.getAmount());
                System.out.println(op.getType());
            });
        } else {
            System.out.println("BankAccount not found!");
        }

    }
}
