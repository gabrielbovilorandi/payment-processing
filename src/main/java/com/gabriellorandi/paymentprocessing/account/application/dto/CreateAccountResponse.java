package com.gabriellorandi.paymentprocessing.account.application.dto;

import com.gabriellorandi.paymentprocessing.account.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateAccountResponse {

    private UUID id;

    private String documentNumber;

    public static CreateAccountResponse from(Account account) {
        return new CreateAccountResponse(account.getId(), account.getDocumentNumber());
    }

}
