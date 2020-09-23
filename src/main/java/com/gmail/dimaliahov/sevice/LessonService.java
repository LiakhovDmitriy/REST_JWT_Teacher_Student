package com.gmail.dimaliahov.sevice;

import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.model.Status;

import java.util.List;

public interface LessonService {

	Lessons createLesson(Lessons lessons);

	List<Lessons> getAllLessonByStatusAndUser(Status status, Long id);

	void changeStatusForLesson(Long id, Status status);

	List<Lessons> getAllLessonByUserId (Long id);

	void delete(Long id);


}
