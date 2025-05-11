package org.sdia.ebankingbackend.dtos;

import jakarta.persistence.Id;
import lombok.Data;
import org.sdia.ebankingbackend.enums.AccountStatus;

import java.util.Date;


@Data
public  class CurrentBankAccountDTO extends BankAccountDTO{
@Id
    private String id;
    private double balance;
    private Date creationDate;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double OverDraft;
}
