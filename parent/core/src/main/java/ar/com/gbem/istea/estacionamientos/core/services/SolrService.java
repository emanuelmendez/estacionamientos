package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import ar.gob.gbem.istea.estacionamientos.dtos.SearchDTO;

@Service
public class SolrService {

	@Autowired
	private SolrRepo solrRepo;

	@Autowired
	private ReservationsService reservationsService;

	@Autowired
	private DozerUtil mapper;

	public List<ParkingLotResultDTO> findByDistance(final SearchDTO dto) {
		final List<ParkingLotSolr> potentialResults = solrRepo.findByCoordinatesWithin(
				new Point(dto.getLatitude(), dto.getLongitude()), new Distance(dto.getRatio(), Metrics.KILOMETERS));

		if (potentialResults.isEmpty())
			return Collections.emptyList();

		Calendar c = Calendar.getInstance();
		c.setTime(dto.getFromDate());
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int fromHour = c.get(Calendar.HOUR_OF_DAY);

		c.setTime(dto.getToDate());
		int toHour = c.get(Calendar.HOUR_OF_DAY);

		List<ParkingLotSolr> results = new ArrayList<>();
		for (ParkingLotSolr p : potentialResults) {
			if (p.potentialDay(dayOfWeek) && fromHour >= p.getFromHour() && toHour <= p.getToHour()) {
				results.add(p);
			}
		}

		if (results.isEmpty())
			return Collections.emptyList();

		reservationsService.retainByAvailability(results, dto);

		return mapper.map(results, ParkingLotResultDTO.class);
	}

}
