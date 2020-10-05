package com.gmail.dimaliahov.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table (name = "price_list")
@Data
public class PriceListForTeacher extends BaseEntety {

	@Column (name = "timeStart")
	private Date timeStart;

	@Column (name = "timeEnd")
	private Date timeEnd;

	@Column (name = "price")
	private int price;

	@ManyToOne
	@JoinColumn (name = "user_id")
	private User user;

	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		PriceListForTeacher that = (PriceListForTeacher) o;
		return Objects.equals(timeStart, that.timeStart) &&
				Objects.equals(timeEnd, that.timeEnd) &&
				Objects.equals(price, that.price);
	}

	@Override
	public int hashCode () {
		return Objects.hash(super.hashCode(), timeStart, timeEnd, price);
	}

	@Override
	public String toString () {
		return "PriceListForTeacher{" +
				"timeStart=" + timeStart +
				", timeEnd=" + timeEnd +
				", price=" + price +
				'}';
	}
}
