package com.gmail.dimaliahov.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role extends BaseEntety {

	@Column(name = "name")
	private String name;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private List<User> users;

	@Override
	public String toString() {
		return "Role{" +
				"id: " + super.getId() + ", " +
				"name: " + name + "}";
	}
}
