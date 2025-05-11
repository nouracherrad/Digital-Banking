package org.sdia.ebankingbackend.mappers;

import org.sdia.ebankingbackend.dtos.AccountOperationDTO;
import org.sdia.ebankingbackend.dtos.CurrentBankAccountDTO;
import org.sdia.ebankingbackend.dtos.SavingBankAccountDTO;
import org.sdia.ebankingbackend.entities.AccountOperation;
import org.sdia.ebankingbackend.entities.CurrentAccount;
import org.sdia.ebankingbackend.entities.savingAccount;
import org.springframework.beans.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.sdia.ebankingbackend.dtos.CustomerDTO;
import org.sdia.ebankingbackend.entities.Customer;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
 return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;}


    public SavingBankAccountDTO fromSavingBankAccount(savingAccount savingAccountDTO) {
      SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
      BeanUtils.copyProperties(savingAccountDTO, savingBankAccountDTO);
      savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccountDTO.getCustomer()));
      savingBankAccountDTO.setType(savingAccountDTO.getClass().getSimpleName());
      return savingBankAccountDTO;
    }
    public savingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO) {
        savingAccount savingAccount = new savingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));

        return savingAccount;

    }

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());
        return currentBankAccountDTO;

    }
    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO, currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;
    }

}

