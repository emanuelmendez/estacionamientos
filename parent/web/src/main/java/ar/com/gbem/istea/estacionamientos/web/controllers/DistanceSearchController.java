package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.services.SolrService;
import ar.gob.gbem.istea.estacionamientos.dtos.ParkingLotResultDTO;

@RestController
public class DistanceSearchController {

	@Autowired
	private SolrService solrService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody List<ParkingLotResultDTO> search(@RequestParam(required = true) double latitude,
			@RequestParam(required = true) double longitude, @RequestParam(required = true) double ratio) {

		return solrService.findByDistance(latitude, longitude, ratio);
	}

}
