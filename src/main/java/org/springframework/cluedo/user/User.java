package org.springframework.cluedo.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
	private String username;

	@Column(name="password")
	@NotEmpty
	private String password;

	@Column(name="email")
	@NotEmpty
	private String email;

	@Column(name="image_url")
	@Value("${some.key:https://i0.wp.com/researchictafrica.net/wp/wp-content/uploads/2016/10/default-profile-pic.jpg?ssl=1}")
	private String imageurl;

	private Integer enabled;
	
	private String authority;
}
