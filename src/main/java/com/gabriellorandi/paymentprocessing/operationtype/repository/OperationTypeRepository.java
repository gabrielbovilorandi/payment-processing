package com.gabriellorandi.paymentprocessing.operationtype.repository;

import com.gabriellorandi.paymentprocessing.operationtype.domain.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Integer> {
}
