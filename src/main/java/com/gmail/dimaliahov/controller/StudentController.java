package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.CreateLessonDTO;
import com.gmail.dimaliahov.service.serviceForController.StudentControllerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping (value = "/api/student/")
@Slf4j
public class StudentController
{


	private final StudentControllerService studentController;

	@Autowired
	public StudentController (StudentControllerService studentController)
	{
		this.studentController = studentController;
	}

	@PostMapping (value = "create")
	public ResponseEntity<Object> createLessonDTOResponseEntity (@RequestBody CreateLessonDTO lesson, HttpServletRequest req) throws ParseException
	{
		return studentController.createLessonDTOResponseEntity(lesson, req);
	}

	@GetMapping ("teacher")
	public ResponseEntity<Object> getAllAvailableTeacher ()
	{
		return studentController.getAllAvailableTeacher();
	}

	@GetMapping ("teacher/{id}")
	public ResponseEntity<Object> getPriceTeacherById (@PathVariable String id)
	{
		return studentController.getPriceTeacherById(id);
	}

	@GetMapping (value = "myLessons")
	public ResponseEntity<Object> getAllMyLessons (HttpServletRequest req)
	{
		return studentController.getAllMyLessons(req);
	}

	@PostMapping (value = "myLessons")
	public ResponseEntity<Object> postDeleteLesson (@RequestBody List<Long> list, HttpServletRequest req)
	{
		return studentController.postDeleteLesson(list, req);
	}
}
