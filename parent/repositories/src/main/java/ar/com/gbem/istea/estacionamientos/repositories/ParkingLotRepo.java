package ar.com.gbem.istea.estacionamientos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;

public interface ParkingLotRepo extends CrudRepository<User, Long>{

	//Optional<UserParkingLot> getAllParkingLotsById(long id);
	@Query("from ParkingLot p where p.user.token = :subject")
	List<ParkingLot> getParkingLotsBySubject(@Param("subject") String subject);
}
