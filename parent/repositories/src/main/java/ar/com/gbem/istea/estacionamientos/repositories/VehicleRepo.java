package ar.com.gbem.istea.estacionamientos.repositories;

import org.springframework.data.repository.CrudRepository;

import ar.com.gbem.istea.estacionamientos.repositories.model.Vehicle;

public interface VehicleRepo extends CrudRepository<Vehicle, Long>{

}
