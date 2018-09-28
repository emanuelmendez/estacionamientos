package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.services.VehicleService;
import ar.gob.gbem.istea.estacionamientos.dtos.VehicleDTO;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
	@RequestMapping(value = "/{idUser}", method = RequestMethod.GET)
	public ResponseEntity<List<VehicleDTO>> getVehiclesByUser(@PathVariable long idUser){
		List<VehicleDTO> vehicles = vehicleService.getVehiclesByUserId(idUser);
		if(vehicles.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(vehicles, HttpStatus.OK);
	}
}
