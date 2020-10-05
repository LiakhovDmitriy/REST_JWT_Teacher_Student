package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.ChangeStatusListDTO;
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
		Lessons nLesson = new CreateLessonDTO().toLesson(lesson);

		Long userID = Long.valueOf(jwtTokenProvider.getIdUsername(token));
		User user = userService.findById(userID);

		lessonService.createLesson(nLesson);

		user.setLessonToUser(nLesson);
		userRepository.save(user);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Map<Object, Object> response = new HashMap<>();
		response.put("msg", "The lesson was created by - " + user.getUsername() + "; he chose a teacher - " + userService.findById(nLesson.getIdTeacher()).getUsername());
		response.put("timeStart", format.format(nLesson.getDateStart()));
		response.put("timeEnd", format.format(nLesson.getDateEnd()));
		return ResponseEntity.ok(response);
	}

	@GetMapping ("teacher")
	public ResponseEntity<Object> getAllAvailableTeacher () {


		Role r = roleRepository.findByName("ROLE_TEACHER");

		List<User> user = userRepository.getByRole(r);

		Map<Object, Object> returnMap = new HashMap<>();

		for (User u : user) {
			Map<Object, Object> price = new HashMap<>();
			List<PriceListForTeacher> p = priceService.getAllPricesByUserId(u.getId());
			for (int i = 0; i < p.size(); i++) {
				price.put("Price " + i, "Time " + p.get(i).getTime() + " minutes; Price: " + p.get(i).getPrice());
			}
			returnMap.put("Price list '" + u.getUsername() + "', TeacherId " + u.getId(), price);
		}
		return ResponseEntity.ok(returnMap);
	}

	@GetMapping ("teacher/{id}")
	public ResponseEntity<Object> getPriceTeacherById (@PathVariable String id) {
		Role r = roleRepository.findByName("ROLE_TEACHER");

		List<User> user = userRepository.getByRole(r);
		Map<Object, Object> returnMap = new HashMap<>();
		for (User u : user) {
			if (u.getId() == Long.parseLong(id)) {
				Map<Object, Object> price = new HashMap<>();
				List<PriceListForTeacher> p = priceService.getAllPricesByUserId(u.getId());
				for (int i = 0; i < p.size(); i++) {
					price.put("Price " + i, "Time " + p.get(i).getTime() + " minutes; Price: " + p.get(i).getPrice());
				}
				returnMap.put("Price list '" + u.getUsername() + "', TeacherId " + u.getId(), price);
				return ResponseEntity.ok(returnMap);
			}

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

		for (int i = 0; i < l.size() ; i++) {
			if (list.contains(l.get(i).getId())) {
				lessonsRepository.changeStatusForLesson( l.get(i).getId(), Status.NOT_ACTIVE );
				response.put("lesson " + l.get(i).getId(), "Now the status is this: " + Status.NOT_ACTIVE);
			}
		}
		return ResponseEntity.ok(response);
	}
}
