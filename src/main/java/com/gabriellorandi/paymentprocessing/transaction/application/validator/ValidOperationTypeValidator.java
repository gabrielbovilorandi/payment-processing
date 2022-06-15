package com.gabriellorandi.paymentprocessing.transaction.application.validator;

import com.gabriellorandi.paymentprocessing.operationtype.repository.OperationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ValidOperationTypeValidator implements ConstraintValidator<ValidOperationType, Integer> {

    private final OperationTypeRepository repository;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return repository.existsById(value);
    }

}