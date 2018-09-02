package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.services.TestService;
import ar.gob.gbem.istea.estacionamientos.dtos.TestObjectDTO;

@RestController
public class TestController {

	@Autowired
	private TestService testService;

	@RequestMapping(value = "/testservice", method = RequestMethod.GET)
	public List<TestObjectDTO> getTestingObjects() {
		return testService.getAll();
	}

}