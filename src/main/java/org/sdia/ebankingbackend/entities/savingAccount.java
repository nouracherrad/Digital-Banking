package org.sdia.ebankingbackend.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@DiscriminatorValue( "SA")
@Data @AllArgsConstructor  @NoArgsConstructor
public class savingAccount  extends BankAccount{
    private double interestRate;
}
