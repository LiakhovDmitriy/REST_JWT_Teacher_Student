package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.model.Status;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class CreateLessonDTO {

	private String dateStart;

	private String dateEnd;

	private int price;

	private Long idTeacher;

	public Lessons toLesson (CreateLessonDTO lessonDTO) throws ParseException {

		Date dateS = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(lessonDTO.getDateStart());
		Date dateE = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(lessonDTO.getDateEnd());
		Lessons lessons = new Lessons()
				.setPrice(lessonDTO.getPrice())
				.setDateEnd(dateS)
				.setDateStart(dateE)
				.setIdTeacher(lessonDTO.getIdTeacher());
		lessons.setCreated(new Date());
		lessons.setUpdated(new Date());
		lessons.setStatus(Status.CONSIDERATION);
		return lessons;
	}

}
