package com.gmail.dimaliahov.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "lesson")
@Data
@ToString
public class Lessons extends BaseEntety {

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
	private Set<User> user = new HashSet<>();

	public Lessons () {
		super();
	}

	public void setUserToLesson (User user) {
		this.user.add(user);
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

	public Set<User> getUser () {
		return user;
	}

	public Lessons setUser (Set<User> users) {
		this.user = users;
		return this;
	}

	@Override
	public String toString () {
		return "Lessons{" +
				"idTeacher=" + idTeacher +
				", dateStart=" + dateStart +
				", dateEnd=" + dateEnd +
				", price=" + price +
				'}';
	}
}
