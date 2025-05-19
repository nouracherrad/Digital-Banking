package org.sdia.ebankingbackend.web;

import org.apache.catalina.LifecycleState;
import org.sdia.ebankingbackend.dtos.AccountHistoryDTO;
import org.sdia.ebankingbackend.dtos.AccountOperationDTO;
import org.sdia.ebankingbackend.dtos.BankAccountDTO;
import org.sdia.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sdia.ebankingbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin("*")

public class BankAccountRestApi {
    private BankAccountService bankAccountService;
    public BankAccountRestApi(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }
    @GetMapping("/accounts/{accountId}")
public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO>  getHistory(@PathVariable String accountId)  {
        return bankAccountService.accountHistory(accountId);
}

    @GetMapping("/accounts/{accountId}/pageoperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId , @RequestParam(name = "page", defaultValue = "0") int page , @RequestParam(name = "size", defaultValue = "5") int size ) throws BankAccountNotFoundException {

        return bankAccountService.getAccountHistory(accountId,page,size);
    }
}
