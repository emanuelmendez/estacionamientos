package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.exceptions.ParkingLotNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.exceptions.UserNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.services.ParkingLotService;
import ar.com.gbem.istea.estacionamientos.web.Constants;
import ar.gob.gbem.istea.estacionamientos.dtos.ParkingLotDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@RestController
@RequestMapping("/users")
public class ParkingLotController {

	@Autowired
	private ParkingLotService parkingLotService;
	
	@RequestMapping(value = "/parkinglot", method = RequestMethod.GET)
	public ResponseEntity<List<ParkingLotDTO>> getParkingLotsByUser(HttpSession session) throws ParkingLotNotFoundException {
		String subject = (String) session.getAttribute(Constants.SUBJECT);
		List<ParkingLotDTO> parkingLots;
		try {
			parkingLots = parkingLotService.getParkingLotsByUser(subject);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (parkingLots.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(parkingLots, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/parkinglot", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<String> addNewParkingLot(HttpSession session,
			@RequestBody(required = true) List<ParkingLotDTO> parkingData) {
		UserResultDTO lender = (UserResultDTO) session.getAttribute(Constants.USER);
		String subject = (String) session.getAttribute(Constants.SUBJECT);
		try {
			parkingLotService.addParkingLot(subject, lender, parkingData);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/parkinglot/{idParkinglot}", method = RequestMethod.PATCH, consumes = {
	"application/json" })
	public ResponseEntity<String> editParkingLot(HttpSession session, @PathVariable Long idParkinglot,
			@RequestBody(required = true) ParkingLotDTO parkingData) {
		UserResultDTO lender = (UserResultDTO) session.getAttribute(Constants.USER);
		try {
			parkingLotService.editUserParkingLot(lender, idParkinglot, parkingData);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
