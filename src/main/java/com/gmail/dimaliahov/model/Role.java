package com.gmail.dimaliahov.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode (callSuper = true)
@Entity
@Table (name = "role")
@Data
public class Role extends BaseEntity
{

	@Column (name = "name")
	private String name;

	@ManyToMany
	@ToString.Exclude
	private List<User> user = new ArrayList<>();

	public void setUserToRole (User user)
	{
		this.user.add(user);
	}

}
