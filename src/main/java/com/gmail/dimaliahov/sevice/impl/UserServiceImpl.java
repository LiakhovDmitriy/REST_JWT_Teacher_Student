package com.gmail.dimaliahov.sevice.impl;

import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.repository.RoleRepository;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl (UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User registration (User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setStatus(Status.ACTIVE);
		User registeredUser = userRepository.save(user);
		log.info("IN register - user: {} successfully registered", registeredUser);
		return registeredUser;
	}

	@Override
	public User findByUsername (String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findById (Long id) {
		return userRepository.findById(id).orElse(null);
	}

}
