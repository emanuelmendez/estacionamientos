package ar.com.gbem.istea.estacionamientos.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;

@Repository
public interface ParkingLotsRepo extends CrudRepository<ParkingLot, Long> {

}
