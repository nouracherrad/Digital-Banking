package org.sdia.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sdia.ebankingbackend.entities.BankAccount;
import org.sdia.ebankingbackend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {
     private Long id;
     private Date operationDate;
     private double amount;
     private String description;
     private OperationType type;

}
