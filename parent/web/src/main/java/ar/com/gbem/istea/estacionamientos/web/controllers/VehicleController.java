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
import ar.com.gbem.istea.estacionamientos.core.exceptions.VehicleNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.services.VehicleService;
import ar.gob.gbem.istea.estacionamientos.dtos.VehicleDTO;

@RestController
@RequestMapping("/users")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;

	@RequestMapping(value = "/{idUser}/vehicles", method = RequestMethod.GET)
	public ResponseEntity<List<VehicleDTO>> getVehiclesByUser(@PathVariable long idUser) {
		List<VehicleDTO> vehicles;
		try {
			vehicles = vehicleService.getVehiclesByUserId(idUser);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (vehicles.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(vehicles, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/vehicles/{idVehicle}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserVehicleById(@PathVariable Long id, @PathVariable Long idVehicle) {
		try {
			vehicleService.deleteUserVehicle(id, idVehicle);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (VehicleNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}/vehicles", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<String> addNewVehicle(@PathVariable Long id,
			@RequestBody(required = true) VehicleDTO vehicleData) {
		try {
			vehicleService.addUserVehicle(id, vehicleData);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}/vehicles/{idVehicle}", method = RequestMethod.PATCH, consumes = {
			"application/json" })
	public ResponseEntity<String> editVehicle(@PathVariable Long id, @PathVariable Long idVehicle,
			@RequestBody(required = true) VehicleDTO vehicleData) {
		try {
			vehicleService.editUserVehicle(id, idVehicle, vehicleData);
		} catch (UserNotFoundException | VehicleNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}