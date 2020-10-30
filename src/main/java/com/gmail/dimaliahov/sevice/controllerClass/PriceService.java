package com.gmail.dimaliahov.sevice.controllerClass;

import com.gmail.dimaliahov.model.PriceListForTeacher;

import java.util.List;

public interface PriceService {

	List<PriceListForTeacher> getAllPricesByUserId (Long id);

}
