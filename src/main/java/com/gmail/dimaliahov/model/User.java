package com.gmail.dimaliahov.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode (callSuper = true)
@Entity
@Table (name = "user")
@Data
@ToString
public class User extends BaseEntity
{

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

	@Column (name = "money")
	private int money;

	@ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable (name = "role_user",
			joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn (name = "role_id", referencedColumnName = "id")})
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Role> role = new ArrayList<>();


	@ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable (name = "user_lesson",
			joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn (name = "lesson_id", referencedColumnName = "id")})
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<Lesson> lesson = new HashSet<>();

	@JsonManagedReference
	@OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
	private Set<AvailableTime> available = new HashSet<>();

	@JsonManagedReference
	@OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<PriceListForTeacher> price = new HashSet<>();

	public User ()
	{
		super();
	}

	public void setLessonToUser (Lesson lesson)
	{
		this.lesson.add(lesson);
	}

	public void setAvailableTimeToUser (AvailableTime available)
	{
		this.available.add(available);
		available.setUser(this);
	}

	public void setPriceToUser (PriceListForTeacher price)
	{
		this.price.add(price);
		price.setUser(this);
	}

	public String getUsername ()
	{
		return username;
	}

	public User setUsername (String username)
	{
		this.username = username;
		return this;
	}

	public String getFirstName ()
	{
		return firstName;
	}

	public User setFirstName (String firstName)
	{
		this.firstName = firstName;
		return this;

	}

	public String getLastName ()
	{
		return lastName;
	}

	public User setLastName (String lastName)
	{
		this.lastName = lastName;
		return this;

	}

	public String getEmail ()
	{
		return email;
	}

	public User setEmail (String email)
	{
		this.email = email;
		return this;
	}

	public String getPassword ()
	{
		return password;
	}

	public User setPassword (String password)
	{
		this.password = password;
		return this;
	}

	public List<Role> getRole ()
	{
		return role;
	}

	public void setRole (List<Role> role)
	{
		this.role = role;
	}

	public Set<Lesson> getLesson ()
	{
		return lesson;
	}

	public void setLesson (Set<Lesson> lesson)
	{
		this.lesson = lesson;
	}

	public Set<AvailableTime> getAvailable ()
	{
		return available;
	}

	public void setAvailable (Set<AvailableTime> available)
	{
		this.available = available;
	}

	public int getMoney ()
	{
		return money;
	}

	public void setMoney (int money)
	{
		this.money = money;
	}

	public Set<PriceListForTeacher> getPrice ()
	{
		return price;
	}

	public void setPrice (Set<PriceListForTeacher> price)
	{
		this.price = price;
	}

}
