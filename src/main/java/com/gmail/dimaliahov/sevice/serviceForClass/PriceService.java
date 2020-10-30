package com.gmail.dimaliahov.sevice.serviceForClass;

import com.gmail.dimaliahov.model.PriceListForTeacher;

import java.util.List;

public interface PriceService {

	List<PriceListForTeacher> getAllPricesByUserId (Long id);

}
