package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class TeacherSetAvailableTimeDTO {

	private String timeStart;

	private String timeEnd;

}
