package ar.com.gbem.istea.estacionamientos.core.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gbem.istea.estacionamientos.core.DozerUtil;
import ar.com.gbem.istea.estacionamientos.core.exceptions.ParkingLotNotFoundException;
import ar.com.gbem.istea.estacionamientos.core.exceptions.UserNotFoundException;
import ar.com.gbem.istea.estacionamientos.repositories.ParkingLotRepo;
import ar.com.gbem.istea.estacionamientos.repositories.ParkingRepository;
import ar.com.gbem.istea.estacionamientos.repositories.model.Address;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;
import ar.com.gbem.istea.estacionamientos.repositories.model.Schedule;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.gob.gbem.istea.estacionamientos.dtos.AddressDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.ParkingLotDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.ScheduleDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@Service
public class ParkingLotService {
	
	@PersistenceContext 
	private EntityManager entityManager;

	@Autowired
	private DozerUtil mapper;
	
	@Autowired
	private ParkingLotRepo parkingLotRepo;
	
	@Autowired
	private ParkingRepository parkingRepository;
	
	@Autowired
	private SolrService solrService;
	
	public List<ParkingLotDTO> getParkingLotsByUser(String subject) throws UserNotFoundException, ParkingLotNotFoundException {
		
		List<ParkingLot> plots = parkingLotRepo.getParkingLotsBySubject(subject);
		List<ParkingLotDTO> list = mapper.map(plots, ParkingLotDTO.class);
		
		for (ParkingLotDTO pl : list) {
			Optional<ParkingLot> parkingLot = parkingRepository.findById(pl.getId());
			ParkingLot parLot = parkingLot.orElseThrow(ParkingLotNotFoundException::new);
			Schedule schedule = parLot.getSchedule();
			Address address = parLot.getAddress();
			
			pl.setAddressDTO(mapper.map(address, AddressDTO.class));
			if(schedule != null) {
				pl.setScheduleDTO(mapper.map(schedule, ScheduleDTO.class));
			}
		}
		return list;
	}
	
	@Transactional
	public void addParkingLot(String subject, UserResultDTO lender, List<ParkingLotDTO> parkingData) throws UserNotFoundException {
		
		Optional<User> u = parkingLotRepo.findById(lender.getId());
		User user = u.orElseThrow(UserNotFoundException::new);
		
		List<ParkingLot> newParkingLotList = mapper.map(parkingData, ParkingLot.class);
		Address address = mapper.map(parkingData.get(0).getAddressDTO(), Address.class);
		
		for(ParkingLot pl : newParkingLotList){
		
			pl.setSince(new Date());
			pl.setUser(user);

			if(address.getId() == 0) {
				pl.setAddress(address);
			}else {
				Address existingAddress = entityManager.find(Address.class, address.getId());
				pl.setAddress(existingAddress);
				entityManager.persist(pl);
			}
			user.getParkingLots().add(pl);
			
			List<ParkingLot> plList = parkingLotRepo.getParkingLotsBySubject(subject);
			
			Schedule schedule = new Schedule();
			
			for(ParkingLotDTO plDto : parkingData) {
				if(pl.getLotNumber() == plDto.getLotNumber()) {
					schedule = mapper.map(plDto.getScheduleDTO(), Schedule.class);
					break;
				}
			}
			
			for(ParkingLot pl2 : plList) {
				if(pl2.getLotNumber() == pl.getLotNumber()) {
					schedule.setParkingLot(pl2);
					pl2.setSchedule(schedule);
					solrService.post(pl2.getId());
					break;
				}
			}
		}
	}
	
	@Transactional
	public void editUserParkingLot(UserResultDTO lender, long lotId, ParkingLotDTO parkingData)
			throws UserNotFoundException {
		Optional<User> u = parkingLotRepo.findById(lender.getId());
		User user = u.orElseThrow(UserNotFoundException::new);
		Schedule schedule = mapper.map(parkingData.getScheduleDTO(), Schedule.class);
		
		for (ParkingLot pl : user.getParkingLots()) {
			if (pl.getId() == lotId) {
				pl.getSchedule().setMonday(schedule.isMonday());
				pl.getSchedule().setTuesday(schedule.isTuesday());
				pl.getSchedule().setWednesday(schedule.isWednesday());
				pl.getSchedule().setThursday(schedule.isThursday());
				pl.getSchedule().setFriday(schedule.isFriday());
				pl.getSchedule().setSaturday(schedule.isSaturday());
				pl.getSchedule().setSunday(schedule.isSunday());
				pl.getSchedule().setFromHour(schedule.getFromHour());
				pl.getSchedule().setToHour(schedule.getToHour());
				pl.setLotNumber(parkingData.getLotNumber());
				pl.setValue(BigDecimal.valueOf(parkingData.getValue()));
				pl.setDescription(parkingData.getDescription());
				return;
			}
		}
		
		solrService.post(lotId);
	}
	
	@Transactional
	public void deleteLotById(long lotId) throws ParkingLotNotFoundException {
		
		solrService.delete(lotId);
		parkingRepository.deleteById(lotId);
	}
}
