package org.springframework.cluedo.user;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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

	@Column(name="tag",unique=true)
	@NotEmpty
	private String tag;

	@ManyToMany
	@JoinTable(name="user_friends",joinColumns = @JoinColumn(
       name = "id1", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
       name = "id2", referencedColumnName = "id"))
    private List<User> friends;



	private Integer enabled;
	
	private String authority;


	public void addFriend(User friend){
		this.friends.add(friend);
	}
	
}
