package com.gabriellorandi.paymentprocessing.transaction.application.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Check if the operation type is valid, that is, if the operation type exits in database.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidOperationTypeValidator.class)
@Documented
public @interface ValidOperationType {
    String message() default "{operationType.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
