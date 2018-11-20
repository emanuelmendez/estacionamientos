package ar.com.gbem.istea.estacionamientos.core.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.response.DelegationTokenResponse.JsonMapResponseParser;
import org.apache.solr.client.solrj.response.SolrResponseBase;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.ContentStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.stereotype.Service;

import ar.com.gbem.istea.estacionamientos.core.DozerUtil;
import ar.com.gbem.istea.estacionamientos.repositories.SolrRepo;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLotSolr;
import ar.gob.gbem.istea.estacionamientos.dtos.ParkingLotResultDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.SearchDTO;

@Service
public class SolrService {

	private static final String FULL_IMPORT_PATH = "/est_core/dataimport?command=full-import&clean=false&commit=true";

	@Autowired
	private SolrRepo solrRepo;

	@Autowired
	private ReservationsService reservationsService;

	@Autowired
	private DozerUtil mapper;

	@Autowired
	private SolrTemplate solrTemplate;

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

	public void post(final long parkingLotId) {
		final SolrRequest<SolrResponse> request = new FullImportRequest(METHOD.GET, FULL_IMPORT_PATH, parkingLotId);
		solrTemplate.execute(request::process);
	}

	private final class FullImportRequest extends SolrRequest<SolrResponse> {
		private static final long serialVersionUID = 3671199842201314618L;

		private FullImportRequest(METHOD m, String path, long parkingLotId) {
			super(m, path + "&pl_id=" + parkingLotId);
			this.setResponseParser(new JsonMapResponseParser());
		}

		@Override
		public SolrParams getParams() {
			return null;
		}

		@Override
		@SuppressWarnings("all")
		public Collection<ContentStream> getContentStreams() throws IOException {
			return null;
		}

		@Override
		protected SolrResponse createResponse(SolrClient client) {
			return new SolrResponseBase();
		}
	}
}
