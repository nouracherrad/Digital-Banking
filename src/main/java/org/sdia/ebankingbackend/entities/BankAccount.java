package org.sdia.ebankingbackend.entities;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sdia.ebankingbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;
@Transactional
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4 )
@Data
@AllArgsConstructor  @NoArgsConstructor
public abstract class BankAccount {
@Id
    private String id;
    private double balance;
    private Date creationDate;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount" ,fetch = FetchType.LAZY)
    private List<AccountOperation> accountOperations;


}
