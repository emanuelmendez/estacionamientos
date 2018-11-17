package ar.com.gbem.istea.estacionamientos.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;

public interface ParkingRepository extends CrudRepository<ParkingLot, Long>{
	
	Optional<ParkingLotAddress> getAddressById(long id);
}
