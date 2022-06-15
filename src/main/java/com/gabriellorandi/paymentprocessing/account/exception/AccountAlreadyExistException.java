package com.gabriellorandi.paymentprocessing.account.exception;

import com.gabriellorandi.paymentprocessing.common.exception.BusinessException;
import lombok.Getter;

public class AccountAlreadyExistException extends BusinessException {

    @Getter
    private final String documentName;

    public AccountAlreadyExistException(String documentName) {
        super("account.exception.exist", documentName);
        this.documentName = documentName;
    }

}
