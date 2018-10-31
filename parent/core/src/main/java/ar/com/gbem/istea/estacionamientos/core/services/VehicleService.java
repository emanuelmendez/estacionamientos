package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import ar.com.gbem.istea.estacionamientos.repositories.UserVehicle;
import ar.com.gbem.istea.estacionamientos.repositories.VehicleRepository;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.com.gbem.istea.estacionamientos.repositories.model.Vehicle;
import ar.gob.gbem.istea.estacionamientos.dtos.VehicleDTO;

@Service
public class VehicleService {

	private Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public List<VehicleDTO> getVehiclesByUserId(Long id){
		UserVehicle uv = vehicleRepository.findAllUserVehicleById(id);
		List<VehicleDTO> dtos = new ArrayList<>();
		
		List<Vehicle> vehicles = uv.getVehicles();
		
		for(Vehicle ve : vehicles) {
			dtos.add(mapper.map(ve, VehicleDTO.class));
		}
		
		return dtos;
	}
	
	@Transactional
	public void deleteUserVehicle(long userId, long vehicleId) {
		
		Optional<User> u = vehicleRepository.findById(userId);
		
		for (Iterator<Vehicle> iterator = u.get().getVehicles().iterator(); iterator.hasNext();){
			if(iterator.next().getId() == vehicleId) {
				iterator.remove();
				break;
			}
		}
	}
	
	@Transactional
	public void addUserVehicle(long userId, VehicleDTO vehicleData) {
		
		Vehicle newVehicle = mapper.map(vehicleData, Vehicle.class);
		Optional<User> u = vehicleRepository.findById(userId);
		
		u.get().getVehicles().add(newVehicle);
	}
	
	@Transactional
	public void editUserVehicle(long userId, long vehicleId, VehicleDTO vehicleData) {
		
		Vehicle editVehicle = mapper.map(vehicleData, Vehicle.class);
		Optional<User> u = vehicleRepository.findById(userId);
		
		for (Vehicle v : u.get().getVehicles()) {
			if(v.getId() == vehicleId) {
				v.setBrand(editVehicle.getBrand());
				v.setColor(editVehicle.getColor());
				v.setModel(editVehicle.getModel());
				v.setPlate(editVehicle.getPlate());
			}
		}
	}
}
