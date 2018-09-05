package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import ar.com.gbem.istea.estacionamientos.repositories.test.TestObject;
import ar.com.gbem.istea.estacionamientos.repositories.test.TestRepo;
import ar.com.gbem.istea.estacionamientos.repositories.test.TestSolrRepo;
import ar.gob.gbem.istea.estacionamientos.dtos.TestObjectDTO;

@Service
public class TestService {

	@Autowired
	private TestRepo testRepo;

	@Autowired
	private TestSolrRepo testSolrRepo;

	private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	public List<TestObjectDTO> getAll() {
		List<TestObject> objects = testRepo.getAll();

		List<TestObjectDTO> dtos = new ArrayList<>();
		for (TestObject testObject : objects) {
			dtos.add(mapper.map(testObject, TestObjectDTO.class));
		}

		return dtos;
	}

	public List<TestObjectDTO> getAllFromSolr() {
		List<TestObject> objects = testSolrRepo.getAll();

		List<TestObjectDTO> dtos = new ArrayList<>();
		for (TestObject testObject : objects) {
			dtos.add(mapper.map(testObject, TestObjectDTO.class));
		}

		return dtos;
	}

	public TestObjectDTO findById(int id) {
		Optional<TestObject> optional = testSolrRepo.findById(id);
		TestObject obj = optional.orElse(new TestObject());
		return mapper.map(obj, TestObjectDTO.class);

	}

}
