package com.gmail.dimaliahov.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode (callSuper = true)
@Entity
@Table (name = "price_list")
@Data
public class PriceListForTeacher extends BaseEntity
{

	@Column (name = "time")
	private long time;

	@Column (name = "price")
	private int price;

	@ManyToOne
	@JoinColumn (name = "user_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private User user;

}
