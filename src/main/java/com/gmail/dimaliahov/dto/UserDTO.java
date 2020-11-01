package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gmail.dimaliahov.model.User;
import lombok.Data;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class UserDTO
{

	private Long id;

	private String username;

	private String firstName;

	private String lastName;

	private String email;

	public static UserDTO fromUser (User user)
	{
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());

		return userDto;
	}
}
