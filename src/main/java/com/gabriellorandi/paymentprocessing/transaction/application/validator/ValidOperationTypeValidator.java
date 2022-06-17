package com.gabriellorandi.paymentprocessing.transaction.application.validator;

import com.gabriellorandi.paymentprocessing.operationtype.service.OperationTypeCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ValidOperationTypeValidator implements ConstraintValidator<ValidOperationType, Integer> {

    private final OperationTypeCacheService service;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return Objects.nonNull(service.findById(value));
    }

}