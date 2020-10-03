package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.CreateLessonDTO;
import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.sevice.LessonService;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping (value = "/api/student/")
@Slf4j
public class StudentController {

	private final LessonService lessonService;
	private final UserService userService;
	private final UserRepository userRepository;

	@Autowired
	public StudentController (LessonService lessonService, UserService userService, UserRepository userRepository) {
		this.lessonService = lessonService;
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@PostMapping (value = "create")
	public ResponseEntity<Object> createLessonDTOResponseEntity (@RequestBody CreateLessonDTO lesson, HttpSession session) throws ParseException {

		Lessons nLesson = new CreateLessonDTO().toLesson(lesson);

		Long userID = (Long) session.getAttribute("userID");
		User user = userService.findById(userID);

		lessonService.createLesson(nLesson);

		user.setLessonToUser(nLesson);
		nLesson.setUserToLesson(user);
		userRepository.save(user);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Map<Object, Object> response = new HashMap<>();
		response.put("msg", "The lesson was created by - " + user.getUsername() + "; he chose a teacher - " + userService.findById(nLesson.getIdTeacher()).getUsername());
		response.put("timeStart", format.format(nLesson.getDateStart()));
		response.put("timeEnd", format.format(nLesson.getDateEnd()));
		return ResponseEntity.ok(response);
	}
}
