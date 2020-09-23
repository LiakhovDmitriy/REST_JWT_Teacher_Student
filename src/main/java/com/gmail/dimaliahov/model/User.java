package com.gmail.dimaliahov.model;


import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntety {

	@Column (name = "username")
	private String username;

	@Column (name = "first_name")
	private String firstName;

	@Column (name = "last_name")
	private String lastName;

	@Column (name = "email")
	private String email;

	@Column (name = "password")
	private String password;

	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable (name = "user_roles",
			joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn (name = "role_id", referencedColumnName = "id")})
	private List<Role> roles;


	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable (name = "user_lessons",
			joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn (name = "lessons_id", referencedColumnName = "id")})
	private List<Lessons> lessons;


}
