package com.gmail.dimaliahov.repository;

import com.gmail.dimaliahov.model.PriceListForTeacher;
import com.gmail.dimaliahov.model.Status;
import com.gmail.dimaliahov.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceListForTeacher, Long> {

	List<PriceListForTeacher> getAllByUser_Id (Long id);

	List<PriceListForTeacher> getByUser(User user);

	@Modifying
	@Query ("UPDATE PriceListForTeacher c SET c.status = :name WHERE c.id = :id")
	@Transactional
	void changeStatusForPrice (@Param ("id") Long id, @Param ("name") Status name);

}
