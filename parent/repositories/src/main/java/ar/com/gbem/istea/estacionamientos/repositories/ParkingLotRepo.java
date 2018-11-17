package ar.com.gbem.istea.estacionamientos.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.com.gbem.istea.estacionamientos.repositories.model.User;

public interface ParkingLotRepo extends CrudRepository<User, Long>{

	Optional<UserParkingLot> getAllParkingLotsById(long id);
}
