package com.gmail.dimaliahov.sevice.impl;

import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.repository.LessonsRepository;
import com.gmail.dimaliahov.sevice.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LessonServiceImpl implements LessonService {

	private final LessonsRepository lessonsRepository;

	@Autowired
	public LessonServiceImpl (LessonsRepository lessonsRepository) {
		this.lessonsRepository = lessonsRepository;
	}


	@Override
	public Lessons createLesson (Lessons lessons) {
		lessonsRepository.save(lessons);

		log.info("IN createLesson - lessons: {} successfully createLesson", lessons);

		return lessons;
	}

	@Override
	public List<Lessons> getAllLessonByStatusAndUser (Status status, Long id) {
		List<Lessons> returnLessons = lessonsRepository.getAllByStatusAndId(status, id);

		return returnLessons;
	}

	@Override
	public void changeStatusForLesson (Long idLessons, Status status) {
		Lessons les = lessonsRepository.getOne(idLessons);
		les.setStatus(status);
	}

	@Override
	public List<Lessons> getAllLessonByUserId (Long userId) {
		return lessonsRepository.getAllById(userId);
	}

	@Override
	public void delete (Long id) {
		lessonsRepository.deleteById(id);
	}
}
