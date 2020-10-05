package com.gmail.dimaliahov.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table (name = "available_time")
@Data
public class AvailableTime extends BaseEntety {

	@Column (name = "timeStart")
	private Date timeStart;

	@Column (name = "timeEnd")
	private Date timeEnd;

	@ManyToOne
	@JoinColumn (name = "user_id")
	private User user;

	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		AvailableTime that = (AvailableTime) o;
		return Objects.equals(timeStart, that.timeStart) &&
				Objects.equals(timeEnd, that.timeEnd);
	}

	@Override
	public int hashCode () {
		return Objects.hash(super.hashCode(), timeStart, timeEnd, user);
	}

	@Override
	public String toString () {
		return "AvailableTime{" +
				"timeStart=" + timeStart +
				", timeEnd=" + timeEnd +
				'}';
	}
}
