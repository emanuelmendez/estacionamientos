package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.services.UserService;
import ar.com.gbem.istea.estacionamientos.repositories.UserVehicle;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.VehicleDTO;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public @ResponseBody ResponseEntity<String> addNewUser(@RequestBody(required = true) UserResultDTO userData) {
		userService.addUser(userData);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserResultDTO> getUser(@PathVariable Long id) {
		UserResultDTO user = userService.getUserById(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/vehicles", method = RequestMethod.GET)
	public ResponseEntity<List<VehicleDTO>> getVehiclesByUser(@PathVariable Long id) {
		List<VehicleDTO> userVehicle = userService.getVehiclesByIdUser(id);
		if (userVehicle == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userVehicle, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/vehicles/{idVehicle}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserVehicleById(@PathVariable Long id, @PathVariable Long idVehicle) {
		try {
			userService.deleteUserVehicle(id, idVehicle);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}                                                                                                           
	}
	
	@RequestMapping(value = "/{id}/vehicles", method = RequestMethod.POST, consumes = { "application/json" })
	public @ResponseBody ResponseEntity<String> addNewVehicle(@PathVariable Long id, @RequestBody(required = true) VehicleDTO vehicleData) {
		userService.addUserVehicle(id, vehicleData);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}/vehicles/{idVehicle}", method = RequestMethod.PATCH, consumes = { "application/json" })
	public @ResponseBody ResponseEntity<String> editVehicle(@PathVariable Long id, @PathVariable Long idVehicle, @RequestBody(required = true) VehicleDTO vehicleData) {
		userService.editUserVehicle(id, idVehicle, vehicleData);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}