package com.gabriellorandi.paymentprocessing.account.application.dto;

import com.gabriellorandi.paymentprocessing.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AccountResponse {

    private UUID id;

    private String documentNumber;

    public static AccountResponse from(Account account) {
        return new AccountResponse(account.getId(), account.getDocumentNumber());
    }

}
