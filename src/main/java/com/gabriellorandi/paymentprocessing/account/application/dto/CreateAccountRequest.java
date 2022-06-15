package com.gabriellorandi.paymentprocessing.account.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateAccountRequest {

    @NotBlank(message = "{account.documentNumber.notBlank}")
    @Size(min = 11, max = 11, message = "{account.documentNumber.size}")
    @JsonProperty("document_number")
    private String documentNumber;

}
