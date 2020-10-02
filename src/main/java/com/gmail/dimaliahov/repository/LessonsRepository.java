package com.gmail.dimaliahov.repository;

import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LessonsRepository extends JpaRepository<Lessons, Long> {

	List<Lessons> getAllByStatusAndIdTeacher(Status status, Long idUser);

	List<Lessons> getAllById(Long userId);

	Lessons getById(Long id);

	@Modifying
	@Query("UPDATE Lessons c SET c.status = :name WHERE c.id = :id")
	@Transactional
	void changeStatusForLesson(@Param("id") Long id, @Param("name") Status name);


//	@Modifying
//	@Query(
//			value ="UPDATE Lessons SET status WHERE (:id) VALUES (:name)  ",
//			nativeQuery = true)
//	void changeStatusForLesson(@Param ("status") Status status, @Param ("id") Long id);
}
