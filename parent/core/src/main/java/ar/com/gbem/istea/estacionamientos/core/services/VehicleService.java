package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gbem.istea.estacionamientos.core.DozerUtil;
import ar.com.gbem.istea.estacionamientos.core.exceptions.UserNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.exceptions.VehicleNotFoundException;
import ar.com.gbem.istea.estacionamientos.repositories.VehicleRepository;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.com.gbem.istea.estacionamientos.repositories.model.Vehicle;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.VehicleDTO;

@Service
public class VehicleService {

	private static final Logger LOG = LoggerFactory.getLogger(VehicleService.class);

	@Autowired
	private DozerUtil mapper;

	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Transactional(readOnly = true)
	public List<VehicleDTO> getVehiclescByUserSubject(String subject) {
		List<Vehicle> vehicles = vehicleRepository.getVehiclesBySubject(subject);
		return mapper.map(vehicles, VehicleDTO.class);
	}

	@Transactional
	public void deleteUserVehicle(UserResultDTO driverDTO, long vehicleId) throws UserNotFoundException, VehicleNotFoundException {
		
		User user = vehicleRepository.findById(driverDTO.getId()).orElseThrow(IllegalArgumentException::new);

		for (Iterator<Vehicle> iterator = user.getVehicles().iterator(); iterator.hasNext();) {
			if (iterator.next().getId() == vehicleId) {
				iterator.remove();
				return;
			}
		}

		LOG.error("Vehicle not found: id {}", vehicleId);
		throw new VehicleNotFoundException();
	}

	@Transactional
	public void addUserVehicle(UserResultDTO driverDTO, VehicleDTO vehicleData) throws UserNotFoundException {
		
		User user = vehicleRepository.findById(driverDTO.getId()).orElseThrow(IllegalArgumentException::new);

		Vehicle newVehicle = mapper.map(vehicleData, Vehicle.class);
		newVehicle.setUser(user);
		user.getVehicles().add(newVehicle);
		
	}

	@Transactional
	public void editUserVehicle(UserResultDTO driverDTO, long vehicleId, VehicleDTO vehicleData)
			throws UserNotFoundException, VehicleNotFoundException {

		User user = vehicleRepository.findById(driverDTO.getId()).orElseThrow(IllegalArgumentException::new);

		for (Vehicle v : user.getVehicles()) {
			if (v.getId() == vehicleId) {
				v.setBrand(vehicleData.getBrand());
				v.setColor(vehicleData.getColor());
				v.setModel(vehicleData.getModel());
				v.setPlate(vehicleData.getPlate());
				return;
			}
		}

		LOG.error("Vehicle not found: id {}", vehicleId);
		throw new VehicleNotFoundException();
	}
}
