package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.AdminUserDto;
import com.gmail.dimaliahov.dto.AuthenticationRequestDto;
import com.gmail.dimaliahov.dto.UserDto;
import com.gmail.dimaliahov.model.Role;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.security.jwt.JwtTokenProvider;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping (value = "/api/")
@Slf4j
public class Controller {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public Controller (UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@GetMapping (value = "{id}")
	public ResponseEntity<Object> getUserById (@PathVariable (name = "id") Long id) {
		User user = userService.findById(id);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		UserDto result = UserDto.fromUser(user);

		boolean roleAdmin = false;
		for (int i = 0; user.getRoles().size() > i; i++) {
			Role r = user.getRoles().get(i);
			if(r.getName().equals("ROLE_ADMIN")){
				roleAdmin = true;
				break;
			}

		}

		if (roleAdmin) {
			AdminUserDto resultAdmin = AdminUserDto.fromUser(user);
			System.out.println("Test");
			return new ResponseEntity<>(resultAdmin, HttpStatus.OK);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping ("login")
	public ResponseEntity login (@RequestBody AuthenticationRequestDto requestDto) {
		try {
			String username = requestDto.getUsername();
			String password = requestDto.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			User user = userService.findByUsername(username);

			if (user == null) {
				throw new UsernameNotFoundException("User with username: " + username + " not found");
			}

			String token = jwtTokenProvider.createToken(username, user.getRoles());

			Map<Object, Object> response = new HashMap<>();
			response.put("username", username);
			response.put("token", token);
			return ResponseEntity.ok(response);
		} catch (AuthenticationException e){
			throw new BadCredentialsException("Invalid username or password");
		}
	}
}
