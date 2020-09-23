package com.gmail.dimaliahov.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "lessons")
@Data
public class Lessons extends BaseEntety{


	@Column (name = "dateStart")
	private Date dateStart;

	@Column (name = "dateEnd")
	private Date dateEnd;

	@Column (name = "price")
	private int price;


	@ManyToMany (mappedBy = "roles", fetch = FetchType.EAGER)
	private List<User> users;


}
