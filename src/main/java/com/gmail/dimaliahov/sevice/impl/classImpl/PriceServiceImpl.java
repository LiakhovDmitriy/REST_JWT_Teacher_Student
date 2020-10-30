package com.gmail.dimaliahov.sevice.impl.classImpl;

import com.gmail.dimaliahov.model.PriceListForTeacher;
import com.gmail.dimaliahov.repository.PriceRepository;
import com.gmail.dimaliahov.sevice.controllerClass.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PriceServiceImpl implements PriceService {

	private final PriceRepository priceRepository;

	@Autowired
	public PriceServiceImpl (PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	@Override
	public List<PriceListForTeacher> getAllPricesByUserId (Long id) {
		return new ArrayList<>(priceRepository.getAllByUser_Id(id));
	}
}
