package com.gmail.dimaliahov.sevice.controllerService;

import com.gmail.dimaliahov.dto.RegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface RegistrationControllerService {

	ResponseEntity<Object> postChangeStatus (RegistrationDTO registrationDTO);

}
