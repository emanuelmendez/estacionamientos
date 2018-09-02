package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import ar.com.gbem.istea.estacionamientos.repositories.test.TestObject;
import ar.com.gbem.istea.estacionamientos.repositories.test.TestRepo;
import ar.gob.gbem.istea.estacionamientos.dtos.TestObjectDTO;

@Service
public class TestService {

	@Autowired
	private TestRepo testRepo;

	private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	public List<TestObjectDTO> getAll() {
		List<TestObject> objects = testRepo.getAll();

		List<TestObjectDTO> dtos = new ArrayList<>();
		for (TestObject testObject : objects) {
			dtos.add(mapper.map(testObject, TestObjectDTO.class));
		}

		return dtos;
	}

}
