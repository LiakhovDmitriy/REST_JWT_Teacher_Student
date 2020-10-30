package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.ChangeStatusListDTO;
import com.gmail.dimaliahov.dto.PriceListDTO;
import com.gmail.dimaliahov.dto.TeacherSetAvailableTimeDTO;
import com.gmail.dimaliahov.sevice.serviceForController.TeacherControllerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping (value = "/api/teacher/")
@Slf4j
public class TeacherController {

	private final TeacherControllerService controllerService;

	@Autowired
	public TeacherController (TeacherControllerService controllerService) {
		this.controllerService = controllerService;
	}

	@GetMapping (value = "offers")
	public ResponseEntity<Object> getAllByStatusConsideration (HttpServletRequest req) {
		return controllerService.getAllByStatusConsideration(req);
	}

	@PostMapping (value = "offers")
	public ResponseEntity<Object> postChangeStatus (@RequestBody List<ChangeStatusListDTO> statusList, HttpServletRequest req) {
		return controllerService.postChangeStatus(statusList, req);
	}

	@PostMapping (value = "add")
	public ResponseEntity<Object> addFreeTimeToTeacher (@RequestBody List<TeacherSetAvailableTimeDTO> availableList, HttpServletRequest req) {
		return controllerService.addFreeTimeToTeacher(availableList, req);
	}

	@PostMapping (value = "addToPriceList")
	public ResponseEntity<Object> createLessonDTOResponseEntity (@RequestBody PriceListDTO price, HttpServletRequest req) {
		return controllerService.createLessonDTOResponseEntity(price, req);
	}

	@GetMapping (value = "myPriceList")
	public ResponseEntity<Object> getPriseList (HttpServletRequest req) {
		return controllerService.getPriseList(req);
	}

	@PostMapping (value = "myPriceList")
	public ResponseEntity<Object> postDeletePrice (@RequestBody List<Long> list, HttpServletRequest req) {
		return controllerService.postDeletePrice(list, req);
	}
}
