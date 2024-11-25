package com.art.cheric.global.validation.validate;

import com.art.cheric.global.validation.annotation.UniqueElements;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueElementsValidator implements ConstraintValidator<UniqueElements, List<?>> {

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        Set<?> uniqueElements = new HashSet<>(value);
        return uniqueElements.size() == value.size();
    }
}
