package com.gmail.dimaliahov.repository;

import com.gmail.dimaliahov.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername (String username);

	List<User> getAllByRoles(String role);
}
