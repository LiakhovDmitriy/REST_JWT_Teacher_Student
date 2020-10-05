package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gmail.dimaliahov.model.User;
import lombok.Data;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class GetAllTeacherDTO {

	private Long idTeacher;
	private String username;
	private String firstName;
	private String lastName;

	public GetAllTeacherDTO fromUserToDTO (User user) {

		GetAllTeacherDTO dto = new GetAllTeacherDTO();
		dto.setIdTeacher(user.getId());
		dto.setUsername(user.getUsername());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		return dto;
	}


}
