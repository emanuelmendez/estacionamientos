package ar.com.gbem.istea.estacionamientos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.com.gbem.istea.estacionamientos.repositories.model.Vehicle;

@Repository
public interface VehicleRepository extends CrudRepository<User, Long>{
	
	//Optional<UserVehicle> findAllUserVehicleById(long id);

	@Query("from Vehicle v where v.user.token = :subject")
	List<Vehicle> getVehiclesBySubject(@Param("subject") String subject);
}
