package com.gabriellorandi.paymentprocessing.transaction.repository;

import com.gabriellorandi.paymentprocessing.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
