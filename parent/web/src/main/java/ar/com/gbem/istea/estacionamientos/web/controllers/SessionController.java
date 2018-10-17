package ar.com.gbem.istea.estacionamientos.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.services.UserService;
import ar.gob.gbem.istea.estacionamientos.dtos.UserDataDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@RestController
@RequestMapping("/session")
public class SessionController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ResponseEntity<UserDataDTO> getUserData(HttpSession session, Long id) {
		String subject = (String) session.getAttribute("subject");
		UserResultDTO user = userService.findByToken(subject);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		UserDataDTO dto = new UserDataDTO();
		dto.setEmail(user.getEmail());
		dto.setHasParkingLots(false);
		dto.setHasVehicles(true);
		dto.setName(user.getName());
		dto.setSurname(user.getSurname());
		dto.setPhone(user.getPhone());

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Void> signUp(HttpSession session, @RequestBody(required = true) UserDataDTO dto) {
		String subject = (String) session.getAttribute("subject");
		UserResultDTO user = userService.findByToken(subject);
		if (user != null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}

		if (userService.signUp(dto, subject)) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
