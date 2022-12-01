package org.springframework.cluedo.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Constraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cluedo.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity{

	@Column(name="username",unique=true)
	@NotEmpty
	@Size(min=4, max=20)
	private String username;

	@Column(name="password")
	@NotEmpty
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z[0-9]]{8,}$" , message = "Debe contener 8 caractéres, uno mínimo en mayúsculas y otro en número")
	private String password;

	@Column(name="email")
	@NotEmpty
	@Email
	private String email;

	@Column(name="image_url")
	@Value("${some.key:https://i0.wp.com/researchictafrica.net/wp/wp-content/uploads/2016/10/default-profile-pic.jpg?ssl=1}")
	private String imageurl;

	private Integer enabled;
	
	private String authority;
}
