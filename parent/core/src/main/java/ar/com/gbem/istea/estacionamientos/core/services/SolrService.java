package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.solr.core.geo.Point;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import ar.com.gbem.istea.estacionamientos.repositories.SolrRepo;
import ar.com.gbem.istea.estacionamientos.repositories.model.Address;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLotSolr;
import ar.gob.gbem.istea.estacionamientos.dtos.ParkingLotResultDTO;

@Service
public class SolrService {

	@Autowired
	private SolrRepo solrRepo;

	private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	public ParkingLotSolr map(ParkingLot parkingLot) {
		ParkingLotSolr o = new ParkingLotSolr();
		o.setId(parkingLot.getId());
		o.setDescription(parkingLot.getDescription());
		o.setLotNumber(parkingLot.getLotNumber());
		o.setUserId(parkingLot.getUser().getId());

		Address address = parkingLot.getAddress();
		o.setAddressId(address.getId());
		o.setStreetAddress(address.getStreetAddress());

		o.setCoordinates(Double.toString(address.getLatitude()) + "," + Double.toString(address.getLongitude()));

		return o;
	}

	public List<ParkingLotResultDTO> findByDistance(double latitude, double longitude, double ratio) {
		Point p = new Point(latitude, longitude);
		Distance d = new Distance(ratio, Metrics.KILOMETERS);
		List<ParkingLotSolr> results = solrRepo.findByCoordinatesWithin(p, d);

		List<ParkingLotResultDTO> dtos = new ArrayList<>();
		for (ParkingLotSolr result : results) {
			dtos.add(mapper.map(result, ParkingLotResultDTO.class));
		}

		return dtos;
	}

}
