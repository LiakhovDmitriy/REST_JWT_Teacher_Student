package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.ChangeStatusListDTO;
import com.gmail.dimaliahov.dto.PriceListDTO;
import com.gmail.dimaliahov.dto.TeacherSetAvailableTimeDTO;
import com.gmail.dimaliahov.model.*;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping (value = "/api/teacher/")
@Slf4j
public class TeacherController {

	private final LessonService lessonService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public TeacherController (LessonService lessonService, UserService userService, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
		this.lessonService = lessonService;
		this.userService = userService;
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	//	Получить по статусу CONSIDERATION (розглядається),
//	це всі задачі створені студентами які вибрали цього вчителя
	@GetMapping (value = "offers")
	public ResponseEntity<Object> getAllByStatusConsideration (HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(7);
		List<Lessons> list = lessonService.getAllLessonByStatusAndTeacherId(Status.CONSIDERATION,
				Long.valueOf(jwtTokenProvider.getIdUsername(token))
		);

		Map<Object, Object> response = new HashMap<>();
		response.put("msg", list);
		return ResponseEntity.ok(response);
	}


	// Вчитель вибирає що візьме. Змінить статусі на APPROVE якщо прийме предложение. REJECTED якщо відмовиться
	@PostMapping (value = "offers")
	public ResponseEntity<Object> postChangeStatus (@RequestBody List<ChangeStatusListDTO> statusList) {
		Map<Object, Object> response = new HashMap<>();

		for (ChangeStatusListDTO list : statusList) {
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
	public ResponseEntity<Object> addFreeTimeToTeacher (@RequestBody List<TeacherSetAvailableTimeDTO> availableList, HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(7);
		User user = userService.findById(Long.valueOf(jwtTokenProvider.getIdUsername(token)));
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

//	Додає прайс, за певний проміжок часу якась ціна
	@PostMapping (value = "addToPriceList")
	public ResponseEntity<Object> createLessonDTOResponseEntity (@RequestBody PriceListDTO price, HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(7);
		User user = userService.findById(Long.valueOf(jwtTokenProvider.getIdUsername(token)));
		Map<Object, Object> response = new HashMap<>();

		PriceListForTeacher p = new PriceListForTeacher();
		p.setPrice(price.getPrice());
		Date dateS = null;
		Date dateE = null;
		try {
			dateS = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(price.getTimeStart());
			dateE = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(price.getTimeEnd());
		} catch (ParseException e){
			e.printStackTrace();
		}
		p.setTimeStart(dateS);
		p.setTimeEnd(dateE);
		p.setStatus(Status.ACTIVE);
		p.setCreated(new Date());
		p.setUpdated(new Date());

		user.setPriceToUser(p);
		userRepository.save(user);
		response.put("price", price);
		return ResponseEntity.ok(response);
	}

//	Получить прайс лист викладача, ще напевно відсортую по ціні
//	public ResponseEntity<Object> getPriseList(){
//
//	}


}
