package org.sdia.ebankingbackend.services;

import jakarta.transaction.Transactional;
import org.sdia.ebankingbackend.entities.BankAccount;
import org.sdia.ebankingbackend.entities.CurrentAccount;
import org.sdia.ebankingbackend.entities.savingAccount;
import org.sdia.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Transactional
@Service
public class BanService {
    @Autowired
    public BankAccountRepository bankAccountRepository;

    public void Consulter(){
        BankAccount bankAccount = bankAccountRepository.findById("13e56d53-6551-4942-b6b8-f6acb2dabc11").orElse(null);
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
