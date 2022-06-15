package com.gabriellorandi.paymentprocessing.transaction.application.validator;

import com.gabriellorandi.paymentprocessing.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidAccountValidator implements ConstraintValidator<ValidAccount, UUID> {

    private final AccountRepository repository;

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        return repository.existsById(value);
    }
}
