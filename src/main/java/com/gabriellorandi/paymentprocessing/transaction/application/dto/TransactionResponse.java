package com.gabriellorandi.paymentprocessing.transaction.application.dto;

import com.gabriellorandi.paymentprocessing.account.domain.Account;
import com.gabriellorandi.paymentprocessing.operationtype.domain.OperationType;
import com.gabriellorandi.paymentprocessing.transaction.domain.Transaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TransactionResponse {

    private UUID transactionId;

    private UUID accountId;

    private Integer operationTypeId;

    private BigDecimal amount;

    private ZonedDateTime eventDate;

    public static TransactionResponse from(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                Optional.ofNullable(transaction.getAccount()).map(Account::getId).orElse(null),
                Optional.ofNullable(transaction.getOperationType()).map(OperationType::getId).orElse(null),
                transaction.getAmount(),
                transaction.getEventDate());
    }

}
