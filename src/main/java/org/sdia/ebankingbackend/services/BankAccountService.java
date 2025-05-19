package org.sdia.ebankingbackend.services;

import org.sdia.ebankingbackend.dtos.*;
import org.sdia.ebankingbackend.entities.BankAccount;
import org.sdia.ebankingbackend.entities.CurrentAccount;
import org.sdia.ebankingbackend.entities.Customer;
import org.sdia.ebankingbackend.entities.savingAccount;

import java.util.List;

public interface BankAccountService {


    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overdraft , Long customerId);
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate , Long customerId);

    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId);
    void debit(String accountId, double amount, String description);
    void credit(String accountId, double amount, String description);
    void transfer(String accountIdSource, String accountIdDestination, double amount);
    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId);

    CustomerDTO UpdateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO>    accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size);

    List<CustomerDTO> searchCustomers(String keyword);
}
