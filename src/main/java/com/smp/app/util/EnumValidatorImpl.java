package com.smp.app.util;

import java.util.Arrays;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    private EnumValidator validator;

    @Override
    public void initialize(EnumValidator annotation) {
        this.validator = annotation;
    }


    @Override
    public boolean isValid(String val, ConstraintValidatorContext constraintValidatorContext) {
        if (this.validator.nullable() && null == val) {
            return true;
        } else if (null == val) {
            return false;
        }

        Object[] consts = this.validator.clazz().getEnumConstants();

        if (null != consts && consts.length != 0) {
            Optional<Object> opt = Arrays.stream(consts).filter(enumConstant -> val.equals(enumConstant.toString()))
                .findFirst();
            return opt.isPresent();
        }
        return false;
    }
}
