package com.gmail.dimaliahov.sevice.impl;

import com.gmail.dimaliahov.model.Role;
import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.model.User;
import com.gmail.dimaliahov.repository.RoleRepository;
import com.gmail.dimaliahov.repository.UserRepository;
import com.gmail.dimaliahov.sevice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl (UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User registration (User user) {
		Role roleUser = roleRepository.findByName("ROLE_USER");
		List<Role> userRoles = new ArrayList<>();
		userRoles.add(roleUser);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(userRoles);
		user.setStatus(Status.ACTIVE);

		User registeredUser = userRepository.save(user);

		log.info("IN register - user: {} successfully registered", registeredUser);

		return registeredUser;
	}

	@Override
	public List<User> getAll () {
		List<User> result = userRepository.findAll();
		log.info("IN getAll - {} users found", result.size());
		return result;
	}

	@Override
	public List<User> getAllByRole (String role) {
		List<User> returnAllUserByRole = userRepository.getAllByRoles(role);
		return returnAllUserByRole;
	}

	@Override
	public User findByUsername (String username) {
		User result = userRepository.findByUsername(username);
		return result;
	}

	@Override
	public User findById (Long id) {
		User result = userRepository.findById(id).orElse(null);
		if (result == null) {
			return null;
		}
		return result;
	}

	@Override
	public void delete (Long id) {
		userRepository.deleteById(id);
		log.info("IN delete - user with id: {} successfully deleted");
	}
}
