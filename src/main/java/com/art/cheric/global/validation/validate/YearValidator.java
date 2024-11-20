package com.art.cheric.global.validation.validate;

import com.art.cheric.global.validation.annotation.ValidYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class YearValidator implements ConstraintValidator<ValidYear, Year> {
    private int min;
    private int max;

    @Override
    public void initialize(ValidYear constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Year year, ConstraintValidatorContext context) {
        if (year == null) {
            return true;
        }

        int value = year.getValue();

        if(max == 0){
            max = Year.now().getValue();
        }

        return value >= min && value <= max;
    }
}
