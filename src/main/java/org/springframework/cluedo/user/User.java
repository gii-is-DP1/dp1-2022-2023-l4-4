package org.springframework.cluedo.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.cluedo.achievement.Achievement;
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
	@Email
	private String email;


	@Column(name="image_url")
	private String imageurl;

	
	
	private String tag;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name="user_friends",joinColumns = @JoinColumn(
       name = "id1", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(
       name = "id2", referencedColumnName = "id"))
    private List<User> friends;



	private Integer enabled;
	
	private String authority;
  
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Achievement> achievements;


	public void addFriend(User friend){
		if(!this.friends.contains(friend)){
			this.friends.add(friend);
		}
	}
	public void deleteFriend(User friend){

		this.friends.remove(friend);
	}


	public void addAchievement(Achievement achievement){
		this.achievements.add(achievement);
	}

}
