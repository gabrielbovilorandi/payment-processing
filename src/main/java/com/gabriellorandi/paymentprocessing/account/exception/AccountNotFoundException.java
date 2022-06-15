package com.gabriellorandi.paymentprocessing.account.exception;

import com.gabriellorandi.paymentprocessing.common.exception.NotFoundException;
import lombok.Getter;

import java.util.UUID;

public class AccountNotFoundException extends NotFoundException {

    @Getter
    private final UUID id;

    public AccountNotFoundException(UUID id) {
        super("account.exception.notFound", id);
        this.id = id;
    }
}
