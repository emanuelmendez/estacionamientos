package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.exceptions.UserNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.services.ParkingLotService;
import ar.gob.gbem.istea.estacionamientos.dtos.ParkingLotDTO;

@RestController
@RequestMapping("/users")
public class ParkingLotController {

	@Autowired
	private ParkingLotService parkingLotService;
	
	@RequestMapping(value = "/{idUser}/parkinglot", method = RequestMethod.GET)
	public ResponseEntity<List<ParkingLotDTO>> getParkingLotsByUser(@PathVariable long idUser) {
		List<ParkingLotDTO> parkingLots;
		try {
			parkingLots = parkingLotService.getParkingLotsByUserId(idUser);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (parkingLots.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(parkingLots, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/parkinglot", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<String> addNewParkingLot(@PathVariable Long id,
			@RequestBody(required = true) List<ParkingLotDTO> parkingData) {
		try {
			parkingLotService.addParkingLot(id, parkingData);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
