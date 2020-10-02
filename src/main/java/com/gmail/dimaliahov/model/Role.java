package com.gmail.dimaliahov.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntety {

	@Column(name = "name")
	private String name;

	@ManyToMany
	@JoinTable (name = "user_roles",
			joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn (name = "role_id", referencedColumnName = "id")})
	private List<User> users = new ArrayList<>();

	public void setUserToRole (User user){
		this.users.add(user);
	}

	@Override
	public String toString () {
		return "Role{" +
				"name='" + name + '\'' +
				'}';
	}
}
