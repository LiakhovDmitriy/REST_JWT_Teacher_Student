package com.gmail.dimaliahov.repository;

import com.gmail.dimaliahov.model.PriceListForTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceListForTeacher, Long> {

	List<PriceListForTeacher> getAllByUser_Id (Long id);

}
