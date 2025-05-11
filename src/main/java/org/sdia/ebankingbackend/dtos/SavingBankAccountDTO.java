package org.sdia.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.Data;
import org.sdia.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;


@Data
public  class SavingBankAccountDTO extends BankAccountDTO {
@Id
    private String id;
    private double balance;
    private Date creationDate;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
