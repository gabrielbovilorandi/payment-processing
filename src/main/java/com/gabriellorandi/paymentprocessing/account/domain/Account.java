package com.gabriellorandi.paymentprocessing.account.domain;

import com.gabriellorandi.paymentprocessing.account.application.dto.CreateAccountRequest;
import com.gabriellorandi.paymentprocessing.common.enums.Status;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Account {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "document_number")
    @Size(min = 11, max = 11)
    private String documentNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public static Account from(@NonNull CreateAccountRequest request) {
        return new Account(null, request.getDocumentNumber(), LocalDateTime.now(), Status.A);
    }

}
