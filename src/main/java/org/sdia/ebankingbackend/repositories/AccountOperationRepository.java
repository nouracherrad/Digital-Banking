package org.sdia.ebankingbackend.repositories;

import org.sdia.ebankingbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository  extends JpaRepository<AccountOperation,Long> {

}
