package com.gmail.dimaliahov.model;


import javax.persistence.*;
import java.util.*;

@Entity
@Table (name = "lesson")
public class Lessons extends BaseEntety{

	@Column (name = "idTeacher")
	private Long idTeacher;

	@Column (name = "dateStart")
	private Date dateStart;

	@Column (name = "dateEnd")
	private Date dateEnd;

	@Column (name = "price")
	private int price;

	@ManyToMany(mappedBy = "lesson" )
//	@JoinTable (name = "user_lessons",
//			joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
//			inverseJoinColumns = {@JoinColumn (name = "lessons_id", referencedColumnName = "id")})
	private Set<User> user = new HashSet<>();

	public Lessons () {
		super();
	}

	public void setUserToLesson(User user){
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
				"dateStart=" + dateStart +
				", dateEnd=" + dateEnd +
				", price=" + price +
				'}';
	}
}
