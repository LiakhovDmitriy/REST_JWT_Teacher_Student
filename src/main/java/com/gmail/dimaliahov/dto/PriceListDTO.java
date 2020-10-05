package com.gmail.dimaliahov.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class PriceListDTO {

	private long time;

	private int price;

}
