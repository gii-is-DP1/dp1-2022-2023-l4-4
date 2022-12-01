package org.springframework.cluedo.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.springframework.cluedo.validatorFunction;
import org.springframework.context.i18n.LocaleContextHolder;

public class UserTest {

    @Test
    public void shouldNotValidateWhenParametersAreEmpty() {

        LocaleContextHolder.setLocale(Locale.ENGLISH);
        User user = new User();
        user.setUsername("");
        user.setEmail("");
        user.setPassword("");

        Validator validator = validatorFunction.createValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(3);

        assertAll("constrainViolations",
                () -> {
                    List<String> violationsList = constraintViolations.stream()
                            .filter(c -> c.getPropertyPath().toString().equals("email")).map(v -> v.getMessage())
                            .collect(Collectors.toList());
                    assertThat(violationsList).containsExactlyInAnyOrder("must not be empty");
                },
                () -> {
                    List<String> violationsList = constraintViolations.stream()
                            .filter(c -> c.getPropertyPath().toString().equals("username")).map(v -> v.getMessage())
                            .collect(Collectors.toList());
                    assertThat(violationsList).containsExactlyInAnyOrder("must not be empty",
                            "size must be between 4 and 20");
                },
                () -> {
                    List<String> violationsList = constraintViolations.stream()
                            .filter(c -> c.getPropertyPath().toString().equals("password")).map(v -> v.getMessage())
                            .collect(Collectors.toList());
                    assertThat(violationsList).containsExactlyInAnyOrder("must not be empty",
                            "Debe contener 8 caractéres, uno mínimo en mayúsculas y otro en número");
                });
    }

}
