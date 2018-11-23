package ar.com.gbem.istea.estacionamientos.repositories;

import org.springframework.data.repository.CrudRepository;

import ar.com.gbem.istea.estacionamientos.repositories.model.Review;

public interface ReviewRepo extends CrudRepository<Review, Long> {

}
