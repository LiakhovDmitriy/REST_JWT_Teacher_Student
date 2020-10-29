package com.gmail.dimaliahov.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@EqualsAndHashCode (callSuper = true)
@Entity
@Table (name = "available_time")
@Data
public class AvailableTime extends BaseEntity {

	@Column (name = "timeStart")
	private Date timeStart;

	@Column (name = "timeEnd")
	private Date timeEnd;

	@ManyToOne
	@JoinColumn (name = "user_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private User user;

}
