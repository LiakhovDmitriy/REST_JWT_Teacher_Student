package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.ChangeStatusList;
import com.gmail.dimaliahov.dto.TeacherSetAvailableTimeDTO;
import com.gmail.dimaliahov.model.AvailableTime;
import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.sevice.LessonService;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping (value = "/api/teacher/")
@Slf4j
public class TeacherController {

	private final LessonService lessonService;
	private final UserService userService;
	private final UserRepository userRepository;

	@Autowired
	public TeacherController (LessonService lessonService, UserService userService, UserRepository userRepository) {
		this.lessonService = lessonService;
		this.userService = userService;
		this.userRepository = userRepository;
	}

//	Получить по статусу CONSIDERATION (розглядається),
//	це всі задачі створені студентами які вибрали цього вчителя
	@GetMapping (value = "offers")
	public ResponseEntity<Object> getAllByStatusConsideration (HttpSession session) {

		List<Lessons> list = lessonService.getAllLessonByStatusAndTeacherId(Status.CONSIDERATION,
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

//	Teacher can set available time
	@GetMapping (value = "add")
	public ResponseEntity<Object> addFreeTimeToTeacher (@RequestBody List<TeacherSetAvailableTimeDTO> availableList, HttpSession session) {
		Long id = (Long) session.getAttribute("userID");
		User user = userService.findById(id);
		Map<Object, Object> response = new HashMap<>();
		for (int i = 0; i < availableList.size(); i++) {
			AvailableTime a = new AvailableTime();

			Date dateS = null;
			Date dateE = null;
			try {
				dateS = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(availableList.get(i).getTimeStart());
				dateE = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(availableList.get(i).getTimeEnd());
			} catch (ParseException e){
				e.printStackTrace();
			}

			a.setTimeStart(dateS);
			a.setTimeEnd(dateE);
			a.setStatus(Status.ACTIVE);
			a.setCreated(new Date());
			a.setUpdated(new Date());
			user.setAvailableTimeToUser(a);
			userRepository.save(user);
			response.put("interval " + i, "Start: " + a.getTimeStart() + " End: " + a.getTimeEnd());
		}

		response.put("msg", "Available time was add!");
		return ResponseEntity.ok(response);
	}




}
