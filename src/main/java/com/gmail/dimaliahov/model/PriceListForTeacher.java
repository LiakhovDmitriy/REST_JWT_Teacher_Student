package com.gmail.dimaliahov.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name = "price_list")
@Data
public class PriceListForTeacher extends BaseEntety {

	@Column (name = "time")
	private long time;

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
		return time == that.time &&
				price == that.price &&
				Objects.equals(user, that.user);
	}

	@Override
	public int hashCode () {
		return Objects.hash(super.hashCode(), time, price);
	}

	@Override
	public String toString () {
		return "PriceListForTeacher{" +
				"time=" + time +
				", price=" + price +
				'}';
	}
}
