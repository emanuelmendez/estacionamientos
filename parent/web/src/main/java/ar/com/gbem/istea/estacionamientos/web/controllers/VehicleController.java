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

import ar.com.gbem.istea.estacionamientos.core.exceptions.UserNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.exceptions.VehicleNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.services.VehicleService;
import ar.com.gbem.istea.estacionamientos.web.Constants;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.VehicleDTO;

@RestController
@RequestMapping("/users")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;

	@RequestMapping(value = "/vehicles", method = RequestMethod.GET)
	public ResponseEntity<List<VehicleDTO>> getVehicleByUser(HttpSession session) {

		String subject = (String) session.getAttribute(Constants.SUBJECT);
		List<VehicleDTO> vehicles = vehicleService.getVehiclescByUserSubject(subject);
		
		if (vehicles.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(vehicles, HttpStatus.OK);
	}

	@RequestMapping(value = "/vehicles/{idVehicle}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserVehicleById(HttpSession session, @PathVariable Long idVehicle) {

		try {
			vehicleService.deleteUserVehicle(idVehicle);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (VehicleNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = "/vehicles", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<String> addNewVehicle(HttpSession session,
			@RequestBody(required = true) VehicleDTO vehicleData) {
		UserResultDTO driver = (UserResultDTO) session.getAttribute(Constants.USER);
		try {
			vehicleService.addUserVehicle(driver, vehicleData);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/vehicles/{idVehicle}", method = RequestMethod.PATCH, consumes = {
			"application/json" })
	public ResponseEntity<String> editVehicle(HttpSession session, @PathVariable Long idVehicle,
			@RequestBody(required = true) VehicleDTO vehicleData) {
		UserResultDTO driver = (UserResultDTO) session.getAttribute(Constants.USER);
		try {
			vehicleService.editUserVehicle(driver, idVehicle, vehicleData);
		} catch (UserNotFoundException | VehicleNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}