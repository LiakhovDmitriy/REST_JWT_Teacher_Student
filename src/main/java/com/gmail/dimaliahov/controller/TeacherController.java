package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.ChangeStatusList;
import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.sevice.LessonService;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping (value = "/api/teacher/")
@Slf4j
public class TeacherController {

	private final LessonService lessonService;
	private final UserService userService;

	@Autowired
	public TeacherController (LessonService lessonService, UserService userService) {
		this.lessonService = lessonService;
		this.userService = userService;
	}

	@GetMapping (value = "consideration")
	public ResponseEntity<Object> getAllByStatusConsideration (HttpSession session) {

		List<Lessons> list = lessonService.getAllLessonByStatusAndTeacherId(Status.CONSIDERATION, (Long) session.getAttribute("userID"));

		Map<Object, Object> response = new HashMap<>();
		response.put("msg", list);
		return ResponseEntity.ok(response);
	}

	@PostMapping (value = "consideration")
	public ResponseEntity<Object> postChangeStatus (@RequestBody ChangeStatusList statusList) {
		Map<Object, Object> response = new HashMap<>();

		List<Long> listLessonId = statusList.getLessonId();

		for (Long j : listLessonId) {
			lessonService.changeStatusForLesson(j, statusList.getStatus());
			response.put("lesson " + j, "Now the status is this: " + statusList.getStatus());
		}
		return ResponseEntity.ok(response);
	}
}
