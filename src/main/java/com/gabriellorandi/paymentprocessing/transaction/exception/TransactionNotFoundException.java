package com.gabriellorandi.paymentprocessing.transaction.exception;

import com.gabriellorandi.paymentprocessing.common.exception.NotFoundException;
import lombok.Getter;

import java.util.UUID;

public class TransactionNotFoundException extends NotFoundException {

    @Getter
    private final UUID id;

    public TransactionNotFoundException(UUID id) {
        super("transaction.exception.notFound", id);
        this.id = id;
    }

}
