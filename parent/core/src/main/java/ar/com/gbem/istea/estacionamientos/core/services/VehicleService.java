package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gbem.istea.estacionamientos.core.DozerUtil;
import ar.com.gbem.istea.estacionamientos.core.exceptions.UserNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.exceptions.VehicleNotFoundException;
import ar.com.gbem.istea.estacionamientos.repositories.UserVehicle;
import ar.com.gbem.istea.estacionamientos.repositories.VehicleRepository;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.com.gbem.istea.estacionamientos.repositories.model.Vehicle;
import ar.gob.gbem.istea.estacionamientos.dtos.VehicleDTO;

@Service
public class VehicleService {

	private static final Logger LOG = LoggerFactory.getLogger(VehicleService.class);

	@Autowired
	private DozerUtil mapper;

	@Autowired
	private VehicleRepository vehicleRepository;

	public List<VehicleDTO> getVehiclesByUserId(Long id) throws UserNotFoundException {
		Optional<UserVehicle> optional = vehicleRepository.findAllUserVehicleById(id);

		UserVehicle uv = optional.orElseThrow(UserNotFoundException::new);

		return mapper.map(uv.getVehicles(), VehicleDTO.class);
	}

	@Transactional
	public void deleteUserVehicle(long userId, long vehicleId) throws UserNotFoundException, VehicleNotFoundException {
		Optional<User> u = vehicleRepository.findById(userId);
		User user = u.orElseThrow(UserNotFoundException::new);

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
	public void addUserVehicle(long userId, VehicleDTO vehicleData) throws UserNotFoundException {
		Optional<User> u = vehicleRepository.findById(userId);
		User user = u.orElseThrow(UserNotFoundException::new);

		Vehicle newVehicle = mapper.map(vehicleData, Vehicle.class);

		user.getVehicles().add(newVehicle);
	}

	@Transactional
	public void editUserVehicle(long userId, long vehicleId, VehicleDTO vehicleData)
			throws UserNotFoundException, VehicleNotFoundException {
		Optional<User> u = vehicleRepository.findById(userId);

		User user = u.orElseThrow(UserNotFoundException::new);

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
