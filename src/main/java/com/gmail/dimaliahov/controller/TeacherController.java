package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.ChangeStatusListDTO;
import com.gmail.dimaliahov.dto.PriceListDTO;
import com.gmail.dimaliahov.dto.TeacherSetAvailableTimeDTO;
import com.gmail.dimaliahov.model.AvailableTime;
import com.gmail.dimaliahov.model.Lesson;
import com.gmail.dimaliahov.model.PriceListForTeacher;
import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.repository.LessonRepository;
import com.gmail.dimaliahov.repository.PriceRepository;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.security.jwt.JwtTokenProvider;
import com.gmail.dimaliahov.sevice.LessonService;
import com.gmail.dimaliahov.sevice.PriceService;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

	private final static int TOKEN_START = 7;

	private final LessonService lessonService;
	private final LessonRepository lessonRepository;
	private final UserService userService;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PriceService priceService;
	private final PriceRepository priceRepository;

	@Autowired
	public TeacherController (LessonService lessonService, LessonRepository lessonRepository, UserService userService, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PriceService priceService, PriceRepository priceRepository) {
		this.lessonService = lessonService;
		this.lessonRepository = lessonRepository;
		this.userService = userService;
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.priceService = priceService;
		this.priceRepository = priceRepository;
	}

	@GetMapping (value = "offers")
	public ResponseEntity<Object> getAllByStatusConsideration (HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(TOKEN_START);
		List<Lesson> list = lessonService.getAllLessonByStatusAndTeacherId(Status.CONSIDERATION,
				Long.valueOf(jwtTokenProvider.getIdUsername(token))
		);
		Map<Object, Object> response = new HashMap<>();
		response.put("msg", list);
		return ResponseEntity.ok(response);
	}

	@PostMapping (value = "offers")
	public ResponseEntity<Object> postChangeStatus (@RequestBody List<ChangeStatusListDTO> statusList, HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(TOKEN_START);
		List<Lesson> l = lessonRepository.getAllByIdTeacher(Long.valueOf(jwtTokenProvider.getIdUsername(token)));
		Map<Object, Object> response = new HashMap<>();

		List<Long> list = statusList.get(0).getLessonId();
		for (Lesson lesson : l) {
			for (Long aLong : list) {
				if (lesson.getId().equals(aLong)) {
					lessonRepository.changeStatusForLesson(lesson.getId(), Status.NOT_ACTIVE);
					response.put("lesson " + lesson.getId(), "Now the status is: " + Status.NOT_ACTIVE);
				}
			}
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping (value = "add")
	public ResponseEntity<Object> addFreeTimeToTeacher (@RequestBody List<TeacherSetAvailableTimeDTO> availableList, HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(TOKEN_START);
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
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			response.put("interval " + i, "Start: " + format.format(a.getTimeStart()) + ";  End: " + format.format(a.getTimeEnd()));
		}
		response.put("msg", "Available time was add!");
		return ResponseEntity.ok(response);
	}

	@PostMapping (value = "addToPriceList")
	public ResponseEntity<Object> createLessonDTOResponseEntity (@RequestBody PriceListDTO price, HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(TOKEN_START);
		User user = userService.findById(Long.valueOf(jwtTokenProvider.getIdUsername(token)));
		Map<Object, Object> response = new HashMap<>();

		PriceListForTeacher p = new PriceListForTeacher();
		p.setPrice(price.getPrice());
		p.setTime(price.getTime());
		p.setStatus(Status.ACTIVE);
		p.setCreated(new Date());
		p.setUpdated(new Date());

		user.setPriceToUser(p);
		userRepository.save(user);
		response.put("price", price);
		return ResponseEntity.ok(response);
	}

	@GetMapping (value = "myPriceList")
	public ResponseEntity<Object> getPriseList (HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(TOKEN_START);
		User user = userService.findById(Long.valueOf(jwtTokenProvider.getIdUsername(token)));
		List<PriceListForTeacher> l = priceService.getAllPricesByUserId(user.getId());

		Map<Object, Object> response = new HashMap<>();
		response.put("My username", user.getUsername());

		Map<Object, Object> date = new HashMap<>();

		for (int i = 0; i < l.size(); i++) {
			date.put("Price " + i, "Time " + l.get(i).getTime() + " minutes; Price: " + l.get(i).getPrice());
		}
		response.put("Prices", date);

		return ResponseEntity.ok(response);
	}

	@PostMapping (value = "myPriceList")
	public ResponseEntity<Object> postDelitedPrice (@RequestBody List<Long> list, HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(TOKEN_START);
		User user = userService.findById(Long.valueOf(jwtTokenProvider.getIdUsername(token)));
		List<PriceListForTeacher> l = priceRepository.getByUser(user);
		Map<Object, Object> response = new HashMap<>();

		for (PriceListForTeacher priceListForTeacher : l) {
			if (list.contains(priceListForTeacher.getId())) {
				priceRepository.changeStatusForPrice(priceListForTeacher.getId(), Status.NOT_ACTIVE);
				response.put("Price " + priceListForTeacher.getId(), "Now the status is this: " + Status.NOT_ACTIVE);
			}
		}

		return ResponseEntity.ok(response);
	}
}
