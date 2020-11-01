package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gmail.dimaliahov.model.Lesson;
import com.gmail.dimaliahov.model.Status;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class CreateLessonDTO
{

	private String dateStart;

	private String dateEnd;

	private int price;

	private Long idTeacher;

	public Lesson toLesson (CreateLessonDTO lessonDTO) throws ParseException
	{

		Date dateS = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(lessonDTO.getDateStart());
		Date dateE = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(lessonDTO.getDateEnd());
		Lesson lesson = new Lesson()
				.setPrice(lessonDTO.getPrice())
				.setDateEnd(dateS)
				.setDateStart(dateE)
				.setIdTeacher(lessonDTO.getIdTeacher());
		lesson.setCreated(new Date());
		lesson.setUpdated(new Date());
		lesson.setStatus(Status.CONSIDERATION);
		return lesson;
	}

}
