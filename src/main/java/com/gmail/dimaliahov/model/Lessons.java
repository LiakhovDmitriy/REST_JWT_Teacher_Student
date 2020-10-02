package com.gmail.dimaliahov.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "lessons")
public class Lessons extends BaseEntety{

	@Column (name = "idTeacher")
	private Long idTeacher;

	@Column (name = "dateStart")
	private Date dateStart;

	@Column (name = "dateEnd")
	private Date dateEnd;

	@Column (name = "price")
	private int price;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "lessons" )
	private List<User> users = new ArrayList<>();

	public Lessons () {
		super();
	}

	public void setUserToLesson(User user){
		users.add(user);
	}



	public Long getIdTeacher () {
		return idTeacher;
	}

	public Lessons setIdTeacher (Long idTeacher) {
		this.idTeacher = idTeacher;
		return this;
	}

	public Date getDateStart () {
		return dateStart;
	}

	public Lessons setDateStart (Date dateStart) {
		this.dateStart = dateStart;
		return this;
	}

	public Date getDateEnd () {
		return dateEnd;
	}

	public Lessons setDateEnd (Date dateEnd) {
		this.dateEnd = dateEnd;
		return this;

	}

	public int getPrice () {
		return price;
	}

	public Lessons setPrice (int price) {
		this.price = price;
		return this;

	}

	public List<User> getUsers () {
		return users;
	}

	public Lessons setUsers (List<User> users) {
		this.users = users;
		return this;

	}

	@Override
	public String toString () {
		return "Lessons{" +
				"dateStart=" + dateStart +
				", dateEnd=" + dateEnd +
				", price=" + price +
				'}';
	}
}
