package ar.com.gbem.istea.estacionamientos.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.exceptions.NotUniquePhoneException;
import ar.com.gbem.istea.estacionamientos.core.exceptions.UserNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.services.UserService;
import ar.gob.gbem.istea.estacionamientos.dtos.UserDataDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@RestController
@RequestMapping("/session")
public class SessionController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ResponseEntity<UserResultDTO> getUserData(HttpSession session) {
		String subject = (String) session.getAttribute("subject");
		try {
			UserResultDTO user = userService.findByToken(subject);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<UserResultDTO> signUp(HttpSession session, @RequestBody(required = true) UserDataDTO dto) {
		String subject = (String) session.getAttribute("subject");
		UserResultDTO user = null;
		try {
			user = userService.findByToken(subject);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			// do nothing
		}

		try {
			user = userService.signUp(dto, subject);
		} catch (NotUniquePhoneException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
