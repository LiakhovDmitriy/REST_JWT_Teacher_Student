package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.CreateLessonDTO;
import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.model.Role;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.repository.RoleRepository;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.security.jwt.JwtTokenProvider;
import com.gmail.dimaliahov.sevice.LessonService;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping (value = "/api/student/")
@Slf4j
public class StudentController {

//	GET отримати всих вчителів та їх прайс

//	Ще один GET можно подивиться час коли доступний викладач

//	Потім студент можить створити зайняття з доступного -
//	(також добавить перевірку чи входить час в рамки в методі createLessonDTOResponseEntity

//	GET створених зайнять

//	POST відмінити створене зайняття

	private final LessonService lessonService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final JwtTokenProvider jwtTokenProvider;


	@Autowired
	public StudentController (LessonService lessonService, UserService userService, UserRepository userRepository, RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider) {
		this.lessonService = lessonService;
		this.userService = userService;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.jwtTokenProvider = jwtTokenProvider;
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
	public ResponseEntity<Object> getAllTeacher () {

		Role r = roleRepository.findByName("ROLE_TEACHER");

		List<User> user = userRepository.getByRole(r);

		Map<Object, Object> returnMap = new HashMap<>();
		for (User u : user) {
			returnMap.put("Teacher " + u.getUsername(), "Id teacher - " + u.getId() + "; Price - " + u.getPrice());
		}

		return ResponseEntity.ok(returnMap);
	}
}
