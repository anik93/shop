package com.alc.shop.validation.impl;

import com.alc.shop.validation.AdultValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AdultValidImpl implements ConstraintValidator<AdultValid, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return ChronoUnit.YEARS.between(value, LocalDate.now()) >= 18;
    }

}
