package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import ar.com.gbem.istea.estacionamientos.repositories.UserRepository;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@Service
public class UserService {

	private Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	@Autowired
	private UserRepository userRepo;
	
	public void addUser(UserResultDTO userData) {
		
		User newUser = mapper.map(userData, User.class);
		
		userRepo.save(newUser);
	}
	
	public List<UserResultDTO> findAllUsers(){
		List<UserResultDTO> dtos = new ArrayList<>();
		
		List<User> users = (List<User>) userRepo.findAll();
			
		for (User user : users) {
			dtos.add(mapper.map(user, UserResultDTO.class));
		}

		return dtos;
	}
	
	public UserResultDTO getUserById(Long id){
		Optional<User> userData = userRepo.findById(id);
		User us = userData.orElse(new User());
		
		return mapper.map(us, UserResultDTO.class);
	}
	
	public UserResultDTO getUserByPhone(String phone){
		User userData = userRepo.findByPhone(phone);
		return mapper.map(userData, UserResultDTO.class);
	}
}