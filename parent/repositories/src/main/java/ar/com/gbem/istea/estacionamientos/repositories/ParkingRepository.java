package ar.com.gbem.istea.estacionamientos.repositories;

import org.springframework.data.repository.CrudRepository;

import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;

public interface ParkingRepository extends CrudRepository<ParkingLot, Long>{
	
}
