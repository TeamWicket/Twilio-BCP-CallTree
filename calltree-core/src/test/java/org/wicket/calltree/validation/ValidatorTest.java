package org.wicket.calltree.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wicket.calltree.dto.ContactDto;
import org.wicket.calltree.enums.Role;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    private Validator validator;
    private ContactDto dto;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        dto = new ContactDto();
        dto.setFirstName("Alessandro");
        dto.setLastName("Arosio");
        dto.setPhoneNumber("+124");
    }

    @Test
    void testChampion_null_POI_return_Success() {
        dto.setRole(Role.CHAMPION);
        Set<ConstraintViolation<ContactDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void testChampion_with_POI_return_Failure() {
        dto.setRole(Role.CHAMPION);
        dto.setPointOfContactId(2L);
        Set<ConstraintViolation<ContactDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }

    @Test
    void testAnnotationManager_with_POI_return_Success() {
        dto.setRole(Role.MANAGER);
        dto.setPointOfContactId(3L);
        Set<ConstraintViolation<ContactDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void testAnnotationManager_null_POI_return_Failure() {
        dto.setRole(Role.MANAGER);
        Set<ConstraintViolation<ContactDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
    }
}
