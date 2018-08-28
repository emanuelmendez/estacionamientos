package ar.com.gbem.istea.estacionamientos.repositories.test;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepo extends CrudRepository<TestObject, Integer> {

	@Query("select t from TestObject t")
	List<TestObject> getAll();

}
