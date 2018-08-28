package ar.com.gbem.istea.estacionamientos.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.services.TestService;

@RestController
public class TestController {

	@Autowired
	private TestService testService;

	@RequestMapping(value = "/testservice", method = RequestMethod.GET)
	public String getTestingObjects() {
		return testService.getAll();
	}

}