package com.gabriellorandi.paymentprocessing.account.domain;

import com.gabriellorandi.paymentprocessing.account.application.dto.CreateAccountRequest;
import com.gabriellorandi.paymentprocessing.common.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class AccountTest {

    @Test
    @DisplayName("Create Account test")
    void createAccount() {
        CreateAccountRequest request = new CreateAccountRequest("Account Name");
        Account account = Account.from(request);

        assertNull(account.getId(), "Account id should be null.");
        assertEquals(account.getDocumentNumber(), request.getDocumentNumber(), "DocumentNumber from Account should be the same as DocumentNumber from CreateAccountRequest.");
        assertEquals(account.getStatus(), Status.A, "Status should be Active.");
        assertNull(account.getCreatedAt(), "CreatedAt should be null.");
    }

    @Test
    @DisplayName("Fail to create Account test")
    void failToCreateAccount() {
        assertThrows(NullPointerException.class, () -> Account.from(null), "NullPointerException should be thrown.");
    }

}