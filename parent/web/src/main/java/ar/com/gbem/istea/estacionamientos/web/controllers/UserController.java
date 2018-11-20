package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.exceptions.UserNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.services.UserService;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserResultDTO> getUser(@PathVariable Long id) {
		UserResultDTO user;
		try {
			user = userService.getUserById(id);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<String> testData(@RequestBody(required = true) TestDataDTO dto) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date from = sdf.parse(dto.getFrom());
		Date to = sdf.parse(dto.getTo());

		return new ResponseEntity<>(userService.generateTestData(dto.getMail(), dto.getStreetAdress(),
				dto.getLongitude(), dto.getLatitude(), from, to), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/test_lender", method = RequestMethod.POST)
	public ResponseEntity<String> testDataLender(@RequestBody(required = true) TestDataDTO dto) {

		return new ResponseEntity<>(userService.generateLender(dto.getStreetAdress(),
				dto.getLongitude(), dto.getLatitude()), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/test_driver", method = RequestMethod.POST)
	public ResponseEntity<String> testDataDriver() throws ParseException {
		return new ResponseEntity<>(userService.generateDriver(), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<TestDataDTO> getTestDataModel() {
		return new ResponseEntity<>(new TestDataDTO(), HttpStatus.OK);
	}

}