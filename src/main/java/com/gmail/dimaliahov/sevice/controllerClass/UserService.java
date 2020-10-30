package com.gmail.dimaliahov.sevice.controllerClass;

import com.gmail.dimaliahov.model.User;


public interface UserService {

	void registration (User user);

	User findByUsername (String username);

	User findById (Long id);

}
