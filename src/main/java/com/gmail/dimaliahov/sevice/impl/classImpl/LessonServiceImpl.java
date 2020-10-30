package com.gmail.dimaliahov.sevice.impl.classImpl;

import com.gmail.dimaliahov.model.Lesson;
import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.repository.LessonRepository;
import com.gmail.dimaliahov.sevice.serviceForClass.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LessonServiceImpl implements LessonService {

	private final LessonRepository lessonRepository;

	@Autowired
	public LessonServiceImpl (LessonRepository lessonRepository) {
		this.lessonRepository = lessonRepository;
	}

	@Override
	public void createLesson (Lesson lesson) {
		lessonRepository.save(lesson);
		log.info("IN createLesson - lessons: {} successfully createLesson", lesson);
	}

	@Override
	public List<Lesson> getAllLessonByStatusAndTeacherId (Status status, Long idTeacher) {
		return lessonRepository.getAllByIdTeacherAndStatus(idTeacher, status);
	}

}
