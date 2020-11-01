package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.AuthenticationRequestDTO;
import com.gmail.dimaliahov.service.serviceForController.LoginControllerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping (value = "/api/")
@Slf4j
public class LoginController
{

	private final LoginControllerService controllerService;

	@Autowired
	public LoginController (LoginControllerService controllerService)
	{
		this.controllerService = controllerService;
	}

	@GetMapping (value = "{id}")
	public ResponseEntity<Object> getUserById (@PathVariable (name = "id") Long id, HttpServletRequest req)
	{
		return controllerService.getUserById(id, req);
	}

	@PostMapping ("login")
	public ResponseEntity<Object> login (@RequestBody AuthenticationRequestDTO requestDto)
	{
		return controllerService.login(requestDto);
	}
}
