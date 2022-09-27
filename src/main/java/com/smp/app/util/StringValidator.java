package com.smp.app.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class StringValidator implements ConstraintValidator<StringValidation, String> {

    private int minLength;
    private int maxLength;

    @Override
    public void initialize(final StringValidation validator) {
        minLength = validator.minLength();
        maxLength = validator.maxLength();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext cxt) {
        return StringUtils.isNotBlank(str) && str.length() >= minLength && str.length() <= maxLength;
    }
}