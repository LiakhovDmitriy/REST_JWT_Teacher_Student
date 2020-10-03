package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class RegistrationDTO {

	private String username;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String role;

	@Override
	public String toString () {
		return "RegistrationDTO{" +
				"username='" + username + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
