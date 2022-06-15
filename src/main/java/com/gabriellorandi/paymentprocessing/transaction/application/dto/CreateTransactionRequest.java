package com.gabriellorandi.paymentprocessing.transaction.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabriellorandi.paymentprocessing.transaction.application.validator.ValidAccount;
import com.gabriellorandi.paymentprocessing.transaction.application.validator.ValidOperationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTransactionRequest {

    @NotNull(message = "{account.id.notNull}")
    @JsonProperty("account_id")
    @ValidAccount
    private UUID accountId;

    @NotNull(message = "{operationType.id.notNull}")
    @JsonProperty("operation_type_id")
    @ValidOperationType
    private Integer operationTypeId;

    @Positive(message = "{transaction.amount.positive}")
    @NotNull(message = "{transaction.amount.notNull}")
    private BigDecimal amount;

}
