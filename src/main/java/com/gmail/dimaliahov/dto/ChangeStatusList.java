package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gmail.dimaliahov.model.Status;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class ChangeStatusList {

	private List<Long> lessonId;

	private Status status;
}
