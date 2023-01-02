package org.springframework.cluedo.user;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameForm {
    @NotEmpty
    String username;

}