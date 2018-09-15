package ar.com.gbem.istea.estacionamientos.repositories;

import java.util.List;

import org.springframework.data.geo.Distance;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLotSolr;

@Repository
public interface SolrRepo extends SolrCrudRepository<ParkingLotSolr, Long> {

	List<ParkingLotSolr> findByCoordinatesWithin(Point p, Distance d);

}
