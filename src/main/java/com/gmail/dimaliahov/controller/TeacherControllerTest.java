package com.gmail.dimaliahov.controller;


import com.gmail.dimaliahov.model.AvailableTime;
import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.sevice.LessonService;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping (value = "/api/teacher/test/")
@Slf4j
public class TeacherControllerTest {

	private final LessonService lessonService;
	private final UserService userService;
	private final UserRepository userRepository;

	public TeacherControllerTest (LessonService lessonService, UserService userService, UserRepository userRepository) {
		this.lessonService = lessonService;
		this.userService = userService;
		this.userRepository = userRepository;
	}


	@GetMapping (value = "add")
	public ResponseEntity<Object> getAllByStatusConsideration (HttpSession session) {

		Long id = (Long) session.getAttribute("userID");
		User user = userService.findById(id);

		AvailableTime a = new AvailableTime();
		a.setCreated(new Date());
		a.setUpdated(new Date());
		a.setStatus(Status.ACTIVE);
		a.setTimeStart(new Date());
		a.setTimeEnd(new Date());

		user.setAvailableTimeToUser(a);
		userRepository.save(user);

		Map<Object, Object> response = new HashMap<>();
		response.put("msg", "Available time add ");
		return ResponseEntity.ok(response);
	}


}
