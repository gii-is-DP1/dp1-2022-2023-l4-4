package org.springframework.cluedo.user;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagForm {
    @NotEmpty
    String tag;

}