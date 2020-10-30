package com.gmail.dimaliahov.sevice.serviceForClass;

import com.gmail.dimaliahov.model.Lesson;
import com.gmail.dimaliahov.model.Status;

import java.util.List;

public interface LessonService {

	void createLesson (Lesson lesson);

	List<Lesson> getAllLessonByStatusAndTeacherId (Status status, Long id);

}
