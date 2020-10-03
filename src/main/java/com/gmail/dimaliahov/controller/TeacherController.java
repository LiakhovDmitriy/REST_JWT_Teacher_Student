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

//	Получить по статусу CONSIDERATION (розглядається),
//	це всі задачі створені студентами які вибрали цього вчителя
	@GetMapping (value = "offers")
	public ResponseEntity<Object> getAllByStatusConsideration (HttpSession session) {

		List<Lessons> list = lessonService.getAllLessonByStatusAndTeacherId(
				Status.CONSIDERATION,
				(Long) session.getAttribute("userID")
		);

		Map<Object, Object> response = new HashMap<>();
		response.put("msg", list);
		return ResponseEntity.ok(response);
	}
// Вчитель вибирає що візьме. Змінить статусі на APPROVE якщо прийме предложение. REJECTED якщо відмовиться
	@PostMapping (value = "offers")
	public ResponseEntity<Object> postChangeStatus (@RequestBody List<ChangeStatusList> statusList) {
		Map<Object, Object> response = new HashMap<>();

		for ( ChangeStatusList list :statusList) {
			List<Long> listLessonId = list.getLessonId();
			for (Long j : listLessonId) {
				lessonService.changeStatusForLesson(j, list.getStatus());
				response.put("lesson " + j, "Now the status is this: " + list.getStatus());
			}
		}
		return ResponseEntity.ok(response);
	}




}
