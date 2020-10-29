package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class AuthenticationRequestDTO {

	@ToString.Exclude
	private Long id;

	private String username;

	private String password;

}
