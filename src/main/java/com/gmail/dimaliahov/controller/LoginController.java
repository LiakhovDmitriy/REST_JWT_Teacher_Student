package com.gmail.dimaliahov.controller;

import com.gmail.dimaliahov.dto.AdminUserDTO;
import com.gmail.dimaliahov.dto.AuthenticationRequestDTO;
import com.gmail.dimaliahov.dto.UserDTO;
import com.gmail.dimaliahov.model.Role;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.repository.RoleRepository;
import com.gmail.dimaliahov.repository.UserRepository;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping (value = "/api/")
@Slf4j
public class LoginController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	@Autowired
	public LoginController (UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, RoleRepository roleRepository, UserRepository userRepository) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	@GetMapping (value = "{id}")
	public ResponseEntity<Object> getUserById (@PathVariable (name = "id") Long id, HttpServletRequest req) {
		String token = req.getHeader("Authorization").substring(7);
		Role g = roleRepository.findByName("ROLE_ADMIN");
		User userEnter = userRepository.getByIdAndRole(Long.valueOf(jwtTokenProvider.getIdUsername(token)), g);
		if (userEnter != null) {
			User user = userService.findById(id);
			if (user == null) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			UserDTO result = UserDTO.fromUser(user);
			boolean roleAdmin = false;
			for (int i = 0; user.getRole().size() > i; i++) {
				Role r = user.getRole().get(i);
				if (r.getName().equals("ROLE_ADMIN")) {
					roleAdmin = true;
					break;
				}
			}
			if (roleAdmin) {
				AdminUserDTO resultAdmin = AdminUserDTO.fromUser(user);
				return new ResponseEntity<>(resultAdmin, HttpStatus.OK);
			}
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		Map<Object, Object> result = new HashMap<>();
		result.put("warn", "You dont have access!");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping ("login")
	public ResponseEntity<Object> login (@RequestBody AuthenticationRequestDTO requestDto) {
		try {
			String username = requestDto.getUsername();
			String password = requestDto.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			User user = userService.findByUsername(username);

			if (user == null) {
				throw new UsernameNotFoundException("User with username: " + username + " not found");
			}
			String token = jwtTokenProvider.createToken(username, user.getRole(), user.getId());

			Map<Object, Object> response = new HashMap<>();
			response.put("username", username);
			response.put("token", token);

			return ResponseEntity.ok(response);
		} catch (AuthenticationException e){
			throw new BadCredentialsException("Invalid username or password");
		}
	}
}
