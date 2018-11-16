package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.services.SolrService;
import ar.gob.gbem.istea.estacionamientos.dtos.ParkingLotResultDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.SearchDTO;

@RestController
public class DistanceSearchController {

	@Autowired
	private SolrService solrService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ParkingLotResultDTO>> search(@RequestParam(required = true) double latitude,
			@RequestParam(required = true) double longitude, @RequestParam(required = true) double ratio,
			@RequestParam(required = true, name = "from_date") Date fromDate,
			@RequestParam(required = true, name = "to_date") Date toDate) {

		final SearchDTO dto = new SearchDTO();
		dto.setLatitude(latitude);
		dto.setLongitude(longitude);
		dto.setRatio(ratio);
		dto.setFromDate(fromDate);
		dto.setToDate(toDate);

		List<ParkingLotResultDTO> results = solrService.findByDistance(dto);
		if (results.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(results, HttpStatus.OK);
	}

}
