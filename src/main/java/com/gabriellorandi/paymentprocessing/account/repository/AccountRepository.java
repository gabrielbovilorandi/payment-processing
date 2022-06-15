package com.gabriellorandi.paymentprocessing.account.repository;

import com.gabriellorandi.paymentprocessing.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByDocumentNumber(String documentNumber);

}
