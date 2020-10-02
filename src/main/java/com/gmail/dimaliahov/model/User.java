package com.gmail.dimaliahov.model;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Table (name = "user")
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

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable (name = "role_user",
			joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn (name = "role_id", referencedColumnName = "id")})
	private List<Role> role = new ArrayList<>();


	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable (name = "user_lesson",
			joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn (name = "lesson_id", referencedColumnName = "id")})
	private Set<Lessons> lesson = new HashSet<>();

	public User () {
		super();
	}

	public void setRoleToUser (Role role) {
		this.role.add(role);
	}

	public void setLessonToUser (Lessons lessons) {
		this.lesson.add(lessons);
	}

//	GETTER AND SETTER
	public String getUsername () {
		return username;
	}

	public User setUsername (String username) {
		this.username = username;
		return this;
	}

	public String getFirstName () {
		return firstName;
	}

	public User setFirstName (String firstName) {
		this.firstName = firstName;
		return this;

	}

	public String getLastName () {
		return lastName;
	}

	public User setLastName (String lastName) {
		this.lastName = lastName;
		return this;

	}

	public String getEmail () {
		return email;
	}

	public User setEmail (String email) {
		this.email = email;
		return this;
	}

	public String getPassword () {
		return password;
	}

	public User setPassword (String password) {
		this.password = password;
		return this;
	}


	@Override
	public String toString () {
		return "User{" +
				"username='" + username + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				'}';
	}


}
