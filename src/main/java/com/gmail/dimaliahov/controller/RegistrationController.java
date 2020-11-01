package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.RegistrationDTO;
import com.gmail.dimaliahov.sevice.serviceForController.RegistrationControllerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "/api/")
@Slf4j
public class RegistrationController
{

	private final RegistrationControllerService controllerService;

	@Autowired
	public RegistrationController (RegistrationControllerService controllerService)
	{
		this.controllerService = controllerService;
	}

	@PostMapping (value = "registration")
	public ResponseEntity<Object> postChangeStatus (@RequestBody RegistrationDTO registrationDTO)
	{
		return controllerService.postChangeStatus(registrationDTO);
	}
}
