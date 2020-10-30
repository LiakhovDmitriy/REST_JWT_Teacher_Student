package com.gmail.dimaliahov.sevice.serviceForController;

import com.gmail.dimaliahov.dto.ChangeStatusListDTO;
import com.gmail.dimaliahov.dto.PriceListDTO;
import com.gmail.dimaliahov.dto.TeacherSetAvailableTimeDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TeacherControllerService {

	ResponseEntity<Object> getAllByStatusConsideration(HttpServletRequest req);

	ResponseEntity<Object> postChangeStatus(List<ChangeStatusListDTO> statusList, HttpServletRequest req);

	ResponseEntity<Object> addFreeTimeToTeacher(List<TeacherSetAvailableTimeDTO> availableList, HttpServletRequest req);

	ResponseEntity<Object> createLessonDTOResponseEntity(PriceListDTO price, HttpServletRequest req);

	ResponseEntity<Object> getPriseList (HttpServletRequest req);

	ResponseEntity<Object> postDeletePrice(List<Long> list, HttpServletRequest req);

}
