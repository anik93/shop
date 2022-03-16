package com.alc.shop.validation;

import com.alc.shop.validation.impl.AdultValidImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdultValidImpl.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdultValid {

    String message() default "You need 18 years old";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
