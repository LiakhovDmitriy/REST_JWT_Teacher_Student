package com.gmail.dimaliahov.controller;


import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.repository.LessonsRepository;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.security.jwt.JwtTokenProvider;
import com.gmail.dimaliahov.sevice.LessonService;
import com.gmail.dimaliahov.sevice.PriceService;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping (value = "/api/student/test/")
@Slf4j
public class TestController {
	private final LessonService lessonService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final PriceService priceService;
	private final LessonsRepository lessonsRepository;

	public TestController (LessonService lessonService, UserService userService, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PriceService priceService, LessonsRepository lessonsRepository) {
		this.lessonService = lessonService;
		this.userService = userService;
		this.userRepository = userRepository;
		this.jwtTokenProvider = jwtTokenProvider;
		this.priceService = priceService;
		this.lessonsRepository = lessonsRepository;
	}



}
