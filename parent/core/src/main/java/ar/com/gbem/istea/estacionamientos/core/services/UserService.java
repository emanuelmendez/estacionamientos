package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import ar.com.gbem.istea.estacionamientos.core.exceptions.NotUniquePhoneException;
import ar.com.gbem.istea.estacionamientos.repositories.UserRepository;
import ar.com.gbem.istea.estacionamientos.repositories.UserVehicle;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.com.gbem.istea.estacionamientos.repositories.model.Vehicle;
import ar.gob.gbem.istea.estacionamientos.dtos.UserDataDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.VehicleDTO;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	@Autowired
	private UserRepository userRepo;

	public UserResultDTO getUserById(Long id) {
		Optional<User> userData = userRepo.findById(id);
		if (userData.isPresent()) {
			return mapper.map(userData.get(), UserResultDTO.class);
		} else {
			return null;
		}
	}

	public boolean existsByPhone(String phone) {
		return userRepo.existsByPhone(phone);
	}

	@Transactional(readOnly = true)
	public UserResultDTO findByToken(String payloadSubject) {
		User u = userRepo.getByToken(payloadSubject);
		return u == null ? null : mapper.map(u, UserResultDTO.class);
	}

	public UserResultDTO signUp(UserDataDTO dto, String subject) throws NotUniquePhoneException {
		if (existsByPhone(dto.getPhone())) {
			throw new NotUniquePhoneException("Phone already taken");
		}
		
		User user = mapper.map(dto, User.class);
		user.setActive(true);
		user.setToken(subject);
		user.setUsername(dto.getEmail());

		try {
			return mapper.map(userRepo.save(user), UserResultDTO.class);
		} catch (Exception e) {
			logger.error("Signup of user failed", e);
			return null;
		}
	}

	public List<VehicleDTO> getVehiclesByIdUser(long id) {
		UserVehicle uv = userRepo.findAllUserVehicleById(id);
		List<VehicleDTO> dtos = new ArrayList<>();
		
		List<Vehicle> vehicles = uv.getVehicles();
		
		for(Vehicle ve : vehicles) {
			dtos.add(mapper.map(ve, VehicleDTO.class));
		}
		
		return dtos;
	}
	
	public void deleteUserVehicle(long userId, long vehicleId) {
		userRepo.deleteUserVehicleById(userId, vehicleId);
	}
	
	public void addUserVehicle(long userId, VehicleDTO vehicleData) {
		Vehicle newVehicle = mapper.map(vehicleData, Vehicle.class);
		
		userRepo.saveUserVehicle(newVehicle.getPlate(), 1, userId, newVehicle.getBrand(), newVehicle.getModel(), newVehicle.getColor());
	}
	
	public void editUserVehicle(long userId, long vehicleId, VehicleDTO vehicleData) {
		Vehicle editVehicle = mapper.map(vehicleData, Vehicle.class);
		
		userRepo.editUserVehicle(vehicleId, editVehicle.getPlate(), userId, editVehicle.getBrand(), editVehicle.getModel(), editVehicle.getColor());
	}
}