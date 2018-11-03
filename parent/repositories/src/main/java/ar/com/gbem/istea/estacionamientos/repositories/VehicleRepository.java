package ar.com.gbem.istea.estacionamientos.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.gbem.istea.estacionamientos.repositories.model.User;

@Repository
public interface VehicleRepository extends CrudRepository<User, Long>{
	
	UserVehicle findAllUserVehicleById(long id);

}
