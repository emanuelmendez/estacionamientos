package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import ar.com.gbem.istea.estacionamientos.repositories.VehicleRepository;
import ar.com.gbem.istea.estacionamientos.repositories.model.Vehicle;
import ar.gob.gbem.istea.estacionamientos.dtos.VehicleDTO;

@Service
public class VehicleService {

	private Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	@Autowired
	private VehicleRepository vehicleRepository; 
	
	public void addVehicle(VehicleDTO vehicleData) {
		
		Vehicle newVehicle = mapper.map(vehicleData, Vehicle.class);
		
		vehicleRepository.save(newVehicle);
	}
	
	public List<VehicleDTO> getVehiclesByUserId(Long id){
		List<VehicleDTO> dtos = new ArrayList<>();
		
		List<Vehicle> vehicles = (List<Vehicle>) vehicleRepository.findByUserId(id);
		
		for(Vehicle ve : vehicles) {
			dtos.add(mapper.map(ve, VehicleDTO.class));
		}
		
		return dtos;
	}
}
