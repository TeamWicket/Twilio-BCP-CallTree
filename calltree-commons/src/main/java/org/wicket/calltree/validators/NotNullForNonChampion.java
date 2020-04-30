package org.wicket.calltree.validators;

import org.wicket.calltree.enums.Role;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotNullForNonChampionValidator.class)
public @interface NotNullForNonChampion {
    String fieldName();
    Role fieldValue();
    String dependFieldName();

    String message() default "{NotNullForNonChampion.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
