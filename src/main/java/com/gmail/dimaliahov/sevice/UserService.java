package com.gmail.dimaliahov.sevice;

import com.gmail.dimaliahov.model.User;


import java.util.List;


public interface UserService  {

	User registration(User user);

	List <User> getAll();

	List<User> getAllByRole(String role);

	User findByUsername(String username);

	User findById(Long id);

	void delete(Long id);

}
