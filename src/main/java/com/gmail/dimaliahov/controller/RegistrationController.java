package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.RegistrationDTO;
import com.gmail.dimaliahov.model.Role;
import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.repository.RoleRepository;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.sevice.LessonService;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping (value = "/api/")
@Slf4j
public class RegistrationController {

	private final LessonService lessonService;
	private final UserService userService;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;


	@Autowired
	public RegistrationController (LessonService lessonService, UserService userService, RoleRepository roleRepository, UserRepository userRepository) {
		this.lessonService = lessonService;
		this.userService = userService;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}



	@PostMapping (value = "registration")
	public ResponseEntity<Object> postChangeStatus (@RequestBody RegistrationDTO registrationDTO) {

		User user = new User()
				.setEmail(registrationDTO.getEmail())
				.setFirstName(registrationDTO.getFirstName())
				.setLastName(registrationDTO.getLastName())
				.setUsername(registrationDTO.getUsername())
				.setPassword(registrationDTO.getPassword());
		user.setStatus(Status.ACTIVE);
		user.setCreated(new Date());
		user.setUpdated(new Date());

		userService.registration(user);

		Role r = roleRepository.findByName("ROLE_" + registrationDTO.getRole());
		User us = userService.findByUsername(user.getUsername());

		r.setUserToRole(us);
		userRepository.save(us);

		Map<Object, Object> response = new HashMap<>();
		User userReturn = userService.findByUsername(registrationDTO.getUsername());
		response.put("user " + registrationDTO.getUsername(), "Registration successful: " + userReturn);
		return ResponseEntity.ok(response);
	}
}
