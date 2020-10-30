package com.gmail.dimaliahov.sevice.impl.controllerImpl;

import com.gmail.dimaliahov.dto.CreateLessonDTO;
import com.gmail.dimaliahov.model.Lesson;
import com.gmail.dimaliahov.model.PriceListForTeacher;
import com.gmail.dimaliahov.model.Role;
import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.repository.LessonRepository;
import com.gmail.dimaliahov.repository.RoleRepository;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.security.jwt.JwtTokenProvider;
import com.gmail.dimaliahov.sevice.controllerClass.LessonService;
import com.gmail.dimaliahov.sevice.controllerClass.PriceService;
import com.gmail.dimaliahov.sevice.controllerClass.UserService;
import com.gmail.dimaliahov.sevice.controllerService.StudentControllerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class StudentControllerServiceImpl implements StudentControllerService {

	private final static int TOKEN_START = 7;

	private final LessonService lessonService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PriceService priceService;
	private final LessonRepository lessonRepository;

	@Autowired
	public StudentControllerServiceImpl (LessonService lessonService, UserService userService, UserRepository userRepository, RoleRepository roleRepository, JwtTokenProvider jwtTokenProvider, PriceService priceService, LessonRepository lessonRepository) {
		this.lessonService = lessonService;
		this.userService = userService;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.priceService = priceService;
		this.lessonRepository = lessonRepository;
	}

	@Override
	public ResponseEntity<Object> createLessonDTOResponseEntity (CreateLessonDTO lesson, HttpServletRequest req) throws ParseException {
		String token = req.getHeader("Authorization").substring(TOKEN_START);
		Map<Object, Object> response = new HashMap<>();
		if (lesson.getDateStart().compareTo(lesson.getDateEnd()) < 0) {
			Lesson nLesson = new CreateLessonDTO().toLesson(lesson);
			Long userId = Long.valueOf(jwtTokenProvider.getIdUsername(token));
			User user = userService.findById(userId);
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

	@Override
	public ResponseEntity<Object> getAllAvailableTeacher () {
		Role r = roleRepository.findByName("ROLE_TEACHER");
		List<User> user = userRepository.getByRole(r);
		Map<Object, Object> returnMap = new HashMap<>();

		for (User u : user) {
			returnMap.put("Available teacher '" + u.getUsername() + "'", "His id: " + u.getId());
		}
		return ResponseEntity.ok(returnMap);
	}

	@Override
	public ResponseEntity<Object> getPriceTeacherById (String id) {
		Map<Object, Object> returnMap = new HashMap<>();
		int h;
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

	@Override
	public ResponseEntity<Object> getAllMyLessons (HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(TOKEN_START);
		Long userID = Long.valueOf(jwtTokenProvider.getIdUsername(token));

		List<Lesson> l = lessonRepository.getByUser(userService.findById(userID));

		Map<Object, Object> response = new HashMap<>();
		response.put("msg", l);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<Object> postDeleteLesson (List<Long> list, HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(TOKEN_START);
		Long userID = Long.valueOf(jwtTokenProvider.getIdUsername(token));
		Map<Object, Object> response = new HashMap<>();
		List<Lesson> l = lessonRepository.getByUser(userService.findById(userID));

		for (Lesson lesson : l) {
			if (list.contains(lesson.getId())) {
				lessonRepository.changeStatusForLesson(lesson.getId(), Status.NOT_ACTIVE);
				response.put("lesson " + lesson.getId(), "Now the status is this: " + Status.NOT_ACTIVE);
			}
		}
		return ResponseEntity.ok(response);
	}
}
