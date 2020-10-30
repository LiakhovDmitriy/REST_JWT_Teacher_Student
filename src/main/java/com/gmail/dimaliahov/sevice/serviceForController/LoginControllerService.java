package com.gmail.dimaliahov.sevice.serviceForController;

import com.gmail.dimaliahov.dto.AuthenticationRequestDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface LoginControllerService {

	ResponseEntity<Object> getUserById(Long id, HttpServletRequest req);

	ResponseEntity<Object> login(AuthenticationRequestDTO requestDto);
}
