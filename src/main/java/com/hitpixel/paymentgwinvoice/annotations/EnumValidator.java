package com.hitpixel.paymentgwinvoice.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull
public @interface EnumValidator {

    Class<? extends Enum<?>> enumClass();
    String message() default "must be any of the allowed values. Check document";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}