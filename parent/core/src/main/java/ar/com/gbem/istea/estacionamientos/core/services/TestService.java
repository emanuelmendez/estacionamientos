package ar.com.gbem.istea.estacionamientos.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.gbem.istea.estacionamientos.repositories.test.TestRepo;

@Service
public class TestService {

	@Autowired
	private TestRepo testRepo;

	public String getAll() {
		return testRepo.getAll().toString();
	}

}
