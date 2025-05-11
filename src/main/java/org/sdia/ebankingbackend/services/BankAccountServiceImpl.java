package org.sdia.ebankingbackend.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sdia.ebankingbackend.dtos.*;
import org.sdia.ebankingbackend.entities.*;
import org.sdia.ebankingbackend.enums.AccountStatus;
import org.sdia.ebankingbackend.enums.OperationType;
import org.sdia.ebankingbackend.exceptions.BalanceNotEnoughException;
import org.sdia.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sdia.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sdia.ebankingbackend.mappers.BankAccountMapperImpl;
import org.sdia.ebankingbackend.repositories.AccountOperationRepository;
import org.sdia.ebankingbackend.repositories.BankAccountRepository;
import org.sdia.ebankingbackend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl  dtolMapper;
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer customer = dtolMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtolMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overdraft, Long customerId) {
            Customer customer=customerRepository.findById(customerId).orElse(null);
            if (customer==null){
                throw new CustomerNotFoundException("Customer not found");
            }
            CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreationDate(new java.util.Date());
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setOverDraft(overdraft);
        currentAccount.setCustomer(customer);
           CurrentAccount   savedBankAccount =  bankAccountRepository.save(currentAccount);
            return dtolMapper.fromCurrentBankAccount(savedBankAccount);
        }


    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        savingAccount savingaccount = new savingAccount();
        savingaccount.setId(UUID.randomUUID().toString());
        savingaccount.setBalance(initialBalance);
        savingaccount.setCreationDate(new java.util.Date());
        savingaccount.setStatus(AccountStatus.CREATED);
        savingaccount.setInterestRate(interestRate);
        savingaccount.setCustomer(customer);
        savingAccount   savedBankAccount =  bankAccountRepository.save(savingaccount);
        return dtolMapper.fromSavingBankAccount(savedBankAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers= customerRepository.findAll();
        List<CustomerDTO> collect = customers.stream().map(customer -> dtolMapper.fromCustomer(customer))
                .collect(Collectors.toList());
return collect;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found!"));

        if (bankAccount instanceof savingAccount) {
            savingAccount savingAccount = (savingAccount) bankAccount;
            return dtolMapper.fromSavingBankAccount(savingAccount);
        } else{
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return dtolMapper.fromCurrentBankAccount(currentAccount);

        }
    }

    @Override
    public void debit(String accountId, double amount, String description) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found!"));
        if (bankAccount.getBalance() < amount) {
            throw new BalanceNotEnoughException("Not enough balance");}else{AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new java.util.Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        accountOperationRepository.save(accountOperation);
        bankAccountRepository.save(bankAccount);}
    }
    @Override
    public void credit(String accountId, double amount, String description) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found!"));
            AccountOperation accountOperation = new AccountOperation();
                accountOperation.setType(OperationType.DEBIT);
                accountOperation.setAmount(amount);
                accountOperation.setDescription(description);
                accountOperation.setOperationDate(new java.util.Date());
                accountOperation.setBankAccount(bankAccount);
                accountOperationRepository.save(accountOperation);
                bankAccount.setBalance(bankAccount.getBalance() - amount);
                accountOperationRepository.save(accountOperation);
                bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) {
        debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
        credit(accountIdDestination, amount, "Transfer from " + accountIdSource);


    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountsDTO = bankAccounts.stream().map(bankAccount ->{
            if (bankAccount instanceof savingAccount) {
                savingAccount savingAccount = (savingAccount) bankAccount;
                return dtolMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtolMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
return bankAccountsDTO;
    }
    @Override
    public CustomerDTO getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found!"));
        return dtolMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO UpdateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer");
        Customer customer = dtolMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtolMapper.fromCustomer(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<AccountOperationDTO>    accountHistory(String accountId) {
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
       return accountOperations.stream().map(op -> dtolMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> dtolMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

}
