package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import ar.com.gbem.istea.estacionamientos.repositories.UserRepository;
import ar.com.gbem.istea.estacionamientos.repositories.UserVehicle;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.com.gbem.istea.estacionamientos.repositories.model.Vehicle;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.VehicleDTO;

@Service
public class UserService {

	private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	@Autowired
	private UserRepository userRepo;

	public void addUser(UserResultDTO userData) {
		User newUser = mapper.map(userData, User.class);

		userRepo.save(newUser);
	}

	public UserResultDTO getUserById(Long id) {
		Optional<User> userData = userRepo.findById(id);
		if (userData.isPresent()) {
			return mapper.map(userData.get(), UserResultDTO.class);
		} else {
			return null;
		}
	}

	public UserResultDTO getUserByPhone(String phone) {
		User userData = userRepo.findByPhone(phone);
		return mapper.map(userData, UserResultDTO.class);
	}
	
	public boolean exists(String payloadSubject) {
		return userRepo.findByToken(payloadSubject) != null;
	}

	public UserResultDTO findByToken(String payloadSubject) {
		User u = userRepo.findByToken(payloadSubject);
		return mapper.map(u, UserResultDTO.class);
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
	
}