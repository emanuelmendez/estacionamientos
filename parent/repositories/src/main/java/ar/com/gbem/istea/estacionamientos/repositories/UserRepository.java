package ar.com.gbem.istea.estacionamientos.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.gbem.istea.estacionamientos.repositories.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByPhone(String phone);

	User findByActive(int active);

	User findByToken(String payloadSubject);

}