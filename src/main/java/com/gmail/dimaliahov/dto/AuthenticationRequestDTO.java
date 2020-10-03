package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class AuthenticationRequestDTO {

	private String username;

	private String password;

	@Override
	public String toString () {
		return "AuthenticationRequestDto{" +
				"username='" + username + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
