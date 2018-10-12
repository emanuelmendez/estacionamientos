package ar.com.gbem.istea.estacionamientos.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gbem.istea.estacionamientos.repositories.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByPhone(String phone);

	User findByActive(int active);

	User findByToken(String payloadSubject);
	
	UserVehicle findAllUserVehicleById(long id);
	
	@Modifying
    @Transactional
    @Query("delete from Vehicle v where v.id = :vehicleId and v.user = :userId")
    void deleteUserVehicleById(@Param("userId") long userId,@Param("vehicleId") long vehicleId);
	
	@Modifying
	@Transactional
	@Query(value = "insert into Vehicle (plate,active,user,brand,model,color) VALUES (:plate,:active,:user,:brand,:model,:color)", nativeQuery = true)
	void saveUserVehicle(@Param("plate")String plate,@Param("active")int active,@Param("user")long user,
		@Param("brand")String brand,@Param("model")String model,@Param("color")String color);
}