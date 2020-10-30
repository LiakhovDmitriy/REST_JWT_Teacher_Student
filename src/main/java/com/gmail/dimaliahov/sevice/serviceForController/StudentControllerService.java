package com.gmail.dimaliahov.sevice.serviceForController;

import com.gmail.dimaliahov.dto.CreateLessonDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

public interface StudentControllerService {

	ResponseEntity<Object> createLessonDTOResponseEntity(CreateLessonDTO lesson, HttpServletRequest req) throws ParseException;

	ResponseEntity<Object> getAllAvailableTeacher();

	ResponseEntity<Object> getPriceTeacherById(String id);

	ResponseEntity<Object> getAllMyLessons(HttpServletRequest req);

	ResponseEntity<Object> postDeleteLesson(List<Long> list, HttpServletRequest req);
}
