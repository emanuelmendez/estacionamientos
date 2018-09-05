package ar.com.gbem.istea.estacionamientos.repositories.test;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestSolrRepo extends SolrCrudRepository<TestObject, Integer> {

	@Query("select t from TestObject t")
	List<TestObject> getAll();

}
