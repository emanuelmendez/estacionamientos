package ar.com.gbem.istea.estacionamientos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.gbem.istea.estacionamientos.repositories.model.Vehicle;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long>{
	
	@Query("SELECT v FROM Vehicle v WHERE v.user = ?1")
    List<Vehicle> findByUserId(Long id);

}
