package com.gmail.dimaliahov.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode (callSuper = true)
@Entity
@Table (name = "lesson")
@Data
@ToString
public class Lesson extends BaseEntity
{

	@Column (name = "idTeacher")
	private Long idTeacher;

	@Column (name = "dateStart")
	private Date dateStart;

	@Column (name = "dateEnd")
	private Date dateEnd;

	@Column (name = "price")
	private int price;

	@JsonBackReference
	@ManyToMany (mappedBy = "lesson")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<User> user = new HashSet<>();

	public Lesson ()
	{
		super();
	}

	public Long getIdTeacher ()
	{
		return idTeacher;
	}

	public Lesson setIdTeacher (Long idTeacher)
	{
		this.idTeacher = idTeacher;
		return this;
	}

	public Date getDateStart ()
	{
		return dateStart;
	}

	public Lesson setDateStart (Date dateStart)
	{
		this.dateStart = dateStart;
		return this;
	}

	public Date getDateEnd ()
	{
		return dateEnd;
	}

	public Lesson setDateEnd (Date dateEnd)
	{
		this.dateEnd = dateEnd;
		return this;

	}

	public Lesson setPrice (int price)
	{
		this.price = price;
		return this;

	}

	public Set<User> getUser ()
	{
		return user;
	}

	public Lesson setUser (Set<User> users)
	{
		this.user = users;
		return this;
	}
}
