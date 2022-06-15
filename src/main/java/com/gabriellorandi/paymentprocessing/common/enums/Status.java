package com.gabriellorandi.paymentprocessing.common.enums;

import lombok.Getter;

@Getter
public enum Status {

    A("ACTIVE"), I("INACTIVE");

    private final String code;

    Status(String code) {
        this.code = code;
    }

}
