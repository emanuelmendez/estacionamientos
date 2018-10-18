package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import ar.com.gbem.istea.estacionamientos.repositories.UserRepository;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.gob.gbem.istea.estacionamientos.dtos.UserDataDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	@Autowired
	private UserRepository userRepo;

	public UserResultDTO getUserById(Long id) {
		Optional<User> userData = userRepo.findById(id);
		if (userData.isPresent()) {
			return mapper.map(userData.get(), UserResultDTO.class);
		} else {
			return null;
		}
	}

	public boolean existsByPhone(String phone) {
		return userRepo.existsByPhone(phone);
	}

	@Transactional(readOnly = true)
	public UserResultDTO findByToken(String payloadSubject) {
		User u = userRepo.getByToken(payloadSubject);
		return u == null ? null : mapper.map(u, UserResultDTO.class);
	}

	public boolean signUp(UserDataDTO dto, String subject) {
		User user = mapper.map(dto, User.class);
		user.setActive(true);
		user.setToken(subject);

		try {
			return userRepo.save(user) != null;
		} catch (Exception e) {
			logger.error("Signup of user failed", e);
			return false;
		}
	}

}