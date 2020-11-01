package com.gmail.dimaliahov.repository;

import com.gmail.dimaliahov.model.Lesson;
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
public interface LessonRepository extends JpaRepository<Lesson, Long>
{

	List<Lesson> getAllByIdTeacherAndStatus (Long idTeacher, Status status);

	List<Lesson> getAllByIdTeacher (Long idTeacher);

	List<Lesson> getByUser (User user);

	@Modifying
	@Query ("UPDATE Lesson c SET c.status = :name WHERE c.id = :id")
	@Transactional
	void changeStatusForLesson (@Param ("id") Long id, @Param ("name") Status name);

}
