package com.gmail.dimaliahov.sevice;

import com.gmail.dimaliahov.model.User;

import java.util.List;


public interface UserService {

	User registration (User user);

	User findByUsername (String username);

	User findById (Long id);

}
