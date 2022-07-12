package com.hitpixel.paymentgwinvoice.annotations;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, String> {

    private Set<String> values;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        values = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return values.contains(value.toLowerCase());
    }

}