package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.stereotype.Service;

import ar.com.gbem.istea.estacionamientos.core.DozerUtil;
import ar.com.gbem.istea.estacionamientos.repositories.SolrRepo;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLotSolr;
import ar.gob.gbem.istea.estacionamientos.dtos.ParkingLotResultDTO;

@Service
public class SolrService {

	@Autowired
	private SolrRepo solrRepo;

	@Autowired
	private DozerUtil mapper;

	public List<ParkingLotResultDTO> findByDistance(final double latitude, final double longitude, final double ratio) {
		final List<ParkingLotSolr> results = solrRepo.findByCoordinatesWithin(new Point(latitude, longitude),
				new Distance(ratio, Metrics.KILOMETERS));

		return mapper.map(results, ParkingLotResultDTO.class);
	}

}
