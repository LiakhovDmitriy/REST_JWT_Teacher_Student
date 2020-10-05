package com.gmail.dimaliahov.sevice;

import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.model.Status;

import java.util.List;

public interface LessonService {

	Lessons createLesson (Lessons lessons);

	List<Lessons> getAllLessonByStatusAndTeacherId (Status status, Long id);

}
