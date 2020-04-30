package org.wicket.calltree.validators;

import org.apache.commons.beanutils.BeanUtils;
import org.wicket.calltree.enums.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class NotNullForNonChampionValidator implements ConstraintValidator<NotNullForNonChampion, Object> {

    private String fieldName;
    private Role expectedFieldValue;
    private String dependFieldName;

    @Override
    public void initialize(NotNullForNonChampion annotation) {
        fieldName = annotation.fieldName();
        expectedFieldValue = annotation.fieldValue();
        dependFieldName = annotation.dependFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {
        if (value == null) {
            return true;
        }
        try {
            String fieldValue = BeanUtils.getProperty(value, fieldName);
            String dependFieldValue = BeanUtils.getProperty(value, dependFieldName);

            // if role IS champion AND P.O.C. is null, validation is passed
            if ((expectedFieldValue.equals(Role.valueOf(fieldValue)) && dependFieldValue == null) ||
                    // if role IS DIFFERENT than champion and P.O.C has a value, validation is passed as well
                    (!(expectedFieldValue.equals(Role.valueOf(fieldValue))) && dependFieldValue != null)) {
                return true;
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }
}
