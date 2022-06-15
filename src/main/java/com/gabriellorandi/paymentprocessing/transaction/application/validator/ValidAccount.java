package com.gabriellorandi.paymentprocessing.transaction.application.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Check if the account is valid, that is, if the account exits in database.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidAccountValidator.class)
@Documented
public @interface ValidAccount {
    String message() default "{account.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
