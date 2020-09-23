package com.gmail.dimaliahov.repository;

import com.gmail.dimaliahov.model.Lessons;
import com.gmail.dimaliahov.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonsRepository extends JpaRepository<Lessons, Long> {

	List<Lessons> getAllByStatusAndId(Status status, Long idUser);

	List<Lessons> getAllById(Long userId);
}
