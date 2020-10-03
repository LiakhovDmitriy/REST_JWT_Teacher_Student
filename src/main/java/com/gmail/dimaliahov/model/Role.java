package com.gmail.dimaliahov.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "role")
@Data
public class Role extends BaseEntety {

	@Column (name = "name")
	private String name;

	@ManyToMany
	private List<User> user = new ArrayList<>();

	public void setUserToRole (User user) {
		this.user.add(user);
	}

	@Override
	public String toString () {
		return "Role{" +
				"name='" + name + '\'' +
				'}';
	}
}
