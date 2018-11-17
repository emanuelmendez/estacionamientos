package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gbem.istea.estacionamientos.core.DozerUtil;
import ar.com.gbem.istea.estacionamientos.core.exceptions.UserNotFoundException;
import ar.com.gbem.istea.estacionamientos.repositories.ParkingLotAddress;
import ar.com.gbem.istea.estacionamientos.repositories.ParkingLotRepo;
import ar.com.gbem.istea.estacionamientos.repositories.ParkingRepository;
import ar.com.gbem.istea.estacionamientos.repositories.UserParkingLot;
import ar.com.gbem.istea.estacionamientos.repositories.model.Address;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.gob.gbem.istea.estacionamientos.dtos.AddressDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.ParkingLotDTO;

@Service
public class ParkingLotService {

	@Autowired
	private DozerUtil mapper;
	
	@Autowired
	private ParkingLotRepo parkingLotRepo;
	
	@Autowired
	private ParkingRepository parkingRepository;
	
	public List<ParkingLotDTO> getParkingLotsByUserId(Long id) throws UserNotFoundException {
		
		Optional<UserParkingLot> optional = parkingLotRepo.getAllParkingLotsById(id);
		UserParkingLot up = optional.orElseThrow(UserNotFoundException::new);
		List<ParkingLotDTO> list = mapper.map(up.getParkingLots(), ParkingLotDTO.class);
		
		for (ParkingLotDTO pl : list) {
			Optional<ParkingLotAddress> op = parkingRepository.getAddressById(pl.getId());
			pl.setAddressDTO(mapper.map(op.get().getAddress(), AddressDTO.class));
		}
		return list;
	}
	
	@Transactional
	public void addParkingLot(long userId, List<ParkingLotDTO> parkingData) throws UserNotFoundException {
		//TODO: agregar a solr
		Optional<User> u = parkingLotRepo.findById(userId);
		User user = u.orElseThrow(UserNotFoundException::new);
		
		List<ParkingLot> newParkingLotList = mapper.map(parkingData, ParkingLot.class);
		Address address = mapper.map(parkingData.get(0).getAddressDTO(), Address.class);
		
		for(ParkingLot pl : newParkingLotList){
			
			pl.setAddress(address);
			pl.setSince(new Date());
			pl.setUser(user);

			user.getParkingLots().add(pl);
		}
	}
}
