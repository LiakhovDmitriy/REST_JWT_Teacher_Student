package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.CreateLessonDTO;
import com.gmail.dimaliahov.model.*;
import com.gmail.dimaliahov.repository.LessonsRepository;
import com.gmail.dimaliahov.repository.RoleRepository;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.security.jwt.JwtTokenProvider;
import com.gmail.dimaliahov.sevice.LessonService;
import com.gmail.dimaliahov.sevice.PriceService;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping (value = "/api/student/")
@Slf4j
public class StudentController {

	private final LessonService lessonService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PriceService priceService;
	private final LessonsRepository lessonsRepository;

	@Autowired
	public StudentController (LessonService lessonService, UserService userService, UserRepository userRepository, RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider, PriceService priceService, LessonsRepository lessonsRepository) {
		this.lessonService = lessonService;
		this.userService = userService;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.priceService = priceService;
		this.lessonsRepository = lessonsRepository;
	}

	@PostMapping (value = "create")
	public ResponseEntity<Object> createLessonDTOResponseEntity (@RequestBody CreateLessonDTO lesson, HttpServletRequest req) throws ParseException {
		String token = req.getHeader("Authorization").substring(7);
		Map<Object, Object> response = new HashMap<>();
		if (lesson.getDateStart().compareTo(lesson.getDateEnd()) < 0) {
			Lessons nLesson = new CreateLessonDTO().toLesson(lesson);
			Long userID = Long.valueOf(jwtTokenProvider.getIdUsername(token));
			User user = userService.findById(userID);
			lessonService.createLesson(nLesson);
			user.setLessonToUser(nLesson);
			userRepository.save(user);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			response.put("msg", "The lesson was created by - " + user.getUsername() + "; he chose a teacher - " + userService.findById(nLesson.getIdTeacher()).getUsername());
			response.put("timeStart", format.format(nLesson.getDateStart()));
			response.put("timeEnd", format.format(nLesson.getDateEnd()));
			return ResponseEntity.ok(response);
		} else {
			response = new HashMap<>();
			response.put("msg", "Incorrect data entry, try to enter the correct time!");
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping ("teacher")
	public ResponseEntity<Object> getAllAvailableTeacher () {

		Role r = roleRepository.findByName("ROLE_TEACHER");
		List<User> user = userRepository.getByRole(r);
		Map<Object, Object> returnMap = new HashMap<>();

		for (User u : user) {
			returnMap.put("Available teacher '" + u.getUsername() + "'", "His id: " + u.getId());
		}
		return ResponseEntity.ok(returnMap);
	}

	@GetMapping ("teacher/{id}")
	public ResponseEntity<Object> getPriceTeacherById (@PathVariable String id) {
		Map<Object, Object> returnMap = new HashMap<>();
		int h = 0;
		try {
			h = Integer.parseInt(id);
		} catch (NumberFormatException e){
			log.warn("NumberFormatException " + e.getMessage());
			returnMap.put("warn", "You entered text instead of a number. Please correct your request, and try again!");
			return ResponseEntity.ok(returnMap);
		}
		if (h > 0) {
			Role r = roleRepository.findByName("ROLE_TEACHER");
			List<User> user = userRepository.getByRole(r);
			for (User u : user) {
				if (u.getId() == Long.parseLong(id)) {
					Map<Object, Object> price = new HashMap<>();
					List<PriceListForTeacher> p = priceService.getAllPricesByUserId(u.getId());
					for (int i = 1; i - 1 < p.size(); i++) {
						price.put("Price " + i, "Time " + p.get(i - 1).getTime() + " minutes; Price: " + p.get(i - 1).getPrice());
					}
					returnMap.put("Teacher: '" + u.getUsername() + "'; His id " + u.getId(), price);
				}
			}
			return ResponseEntity.ok(returnMap);
		}
		returnMap.put("msg", "There is no teacher with such Id");
		return ResponseEntity.ok(returnMap);
	}

	@GetMapping (value = "myLessons")
	public ResponseEntity<Object> getAllMyLessons (HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(7);
		Long userID = Long.valueOf(jwtTokenProvider.getIdUsername(token));

		List<Lessons> l = lessonsRepository.getByUser(userService.findById(userID));

		Map<Object, Object> response = new HashMap<>();
		response.put("msg", l);
		return ResponseEntity.ok(response);
	}

	@PostMapping (value = "myLessons")
	public ResponseEntity<Object> postDeleteLesson (@RequestBody List<Long> list, HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(7);
		Long userID = Long.valueOf(jwtTokenProvider.getIdUsername(token));
		Map<Object, Object> response = new HashMap<>();
		List<Lessons> l = lessonsRepository.getByUser(userService.findById(userID));

		for (Lessons lessons : l) {
			if (list.contains(lessons.getId())) {
				lessonsRepository.changeStatusForLesson(lessons.getId(), Status.NOT_ACTIVE);
				response.put("lesson " + lessons.getId(), "Now the status is this: " + Status.NOT_ACTIVE);
			}
		}
		return ResponseEntity.ok(response);
	}
}
