package com.art.cheric.global.validation.annotation;

import com.art.cheric.global.validation.validate.YearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = YearValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYear {
    String message() default "유효하지 않은 년도입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 1800;

    int max() default 0; // default 값이라면 현재 년도 기준으로 처리
}
