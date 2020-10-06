package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class GetAllTeacherDTO {

	private Long idTeacher;

	private String username;

	private String firstName;

	private String lastName;

}
