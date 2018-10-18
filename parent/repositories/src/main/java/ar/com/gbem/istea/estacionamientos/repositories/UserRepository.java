package ar.com.gbem.istea.estacionamientos.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.gbem.istea.estacionamientos.repositories.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	boolean existsByPhone(String phone);

	User findByActive(int active);

	User getByToken(String payloadSubject);

}