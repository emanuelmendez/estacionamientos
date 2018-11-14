package ar.com.gbem.istea.estacionamientos.core.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gbem.istea.estacionamientos.core.DozerUtil;
import ar.com.gbem.istea.estacionamientos.core.exceptions.NotUniquePhoneException;
import ar.com.gbem.istea.estacionamientos.core.exceptions.UserNotFoundException;
import ar.com.gbem.istea.estacionamientos.repositories.ReservationsRepo;
import ar.com.gbem.istea.estacionamientos.repositories.UserRepository;
import ar.com.gbem.istea.estacionamientos.repositories.model.Address;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;
import ar.com.gbem.istea.estacionamientos.repositories.model.Reservation;
import ar.com.gbem.istea.estacionamientos.repositories.model.Schedule;
import ar.com.gbem.istea.estacionamientos.repositories.model.Status;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.com.gbem.istea.estacionamientos.repositories.model.Vehicle;
import ar.gob.gbem.istea.estacionamientos.dtos.UserDataDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private DozerUtil mapper;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ReservationsRepo reservationsRepo;

	public UserResultDTO getUserById(Long id) throws UserNotFoundException {
		Optional<User> userData = userRepo.findById(id);
		User u = userData.orElseThrow(UserNotFoundException::new);
		return mapper.map(u, UserResultDTO.class);
	}

	public boolean existsByPhone(String phone) {
		return userRepo.existsByPhone(phone);
	}

	@Transactional(readOnly = true)
	public UserResultDTO findByToken(String payloadSubject) throws UserNotFoundException {
		User u = userRepo.getByToken(payloadSubject);

		if (u != null) {
			return mapper.map(u, UserResultDTO.class);
		} else {
			throw new UserNotFoundException();
		}
	}

	public UserResultDTO signUp(UserDataDTO dto, String subject) throws NotUniquePhoneException {
		if (existsByPhone(dto.getPhone())) {
			throw new NotUniquePhoneException("Phone already taken");
		}

		User user = mapper.map(dto, User.class);
		user.setActive(true);
		user.setToken(subject);
		user.setUsername(dto.getEmail());

		try {
			return mapper.map(userRepo.save(user), UserResultDTO.class);
		} catch (Exception e) {
			logger.error("Signup of user failed", e);
			return null;
		}
	}

	@Transactional
	public String generateTestData(String mail, String streetAdress, double longitude, double latitude, Date from,
			Date to) {

		long count = userRepo.count();
		User u = new User();
		u.setActive(true);
		u.setEmail(mail + count + "@gmail.com");
		u.setUsername(mail + count + "@gmail.com");
		u.setSince(new Date());
		u.setLastUpdated(new Date());
		u.setName("Name" + count);
		u.setSurname("Sur" + count);
		double d = Math.random() * count * 12345600;
		u.setPhone(String.valueOf(d));
		u.setToken(String.valueOf(Math.rint(Math.random() * 23 * 31 + count)));

		Vehicle v = new Vehicle();
		v.setActive(true);
		v.setBrand("B" + mail.substring(0, 3));
		v.setColor("#" + count % 10 + "" + count % 10 + "" + count % 10);
		v.setModel("MODEL " + count);
		v.setPlate(count + " " + RandomUtils.nextInt(1, 99999));
		u.getVehicles().add(v);
		v.setUser(u);

		ParkingLot p = new ParkingLot();
		p.setActive(true);
		p.setDescription("Casa n " + count);
		p.setLotNumber(0);
		p.setSince(new Date());

		Schedule schedule = new Schedule();
		schedule.setMonday(true);
		schedule.setTuesday(true);
		schedule.setWednesday(false);
		schedule.setThursday(false);
		schedule.setFriday(true);
		schedule.setSaturday(false);
		schedule.setSunday(false);
		schedule.setFromHour(9);
		schedule.setToHour(18);
		schedule.setParkingLot(p);
		p.setSchedule(schedule);

		Address a = new Address();
		a.setCity("Ciudad de Buenos Aires");
		a.setCountry("Argentina");
		a.setNotes("Puerta color " + v.getColor());
		a.setPostalCode("1234");
		a.setState("Ciudad de Buenos Aires");
		a.setStreetAddress(streetAdress);
		a.setLatitude(latitude);
		a.setLongitude(longitude);

		p.setAddress(a);

		p.setUser(u);
		u.getParkingLots().add(p);

		Reservation r = new Reservation();
		r.setDriver(u);
		User lender = userRepo.findById(1L).orElseThrow(IllegalArgumentException::new);
		r.setLender(lender);
		r.setParkingLot(lender.getParkingLots().get(0));
		r.setFrom(from);
		r.setTo(to);
		r.setValue(BigDecimal.valueOf(2 * count));
		r.setVehicle(v);
		r.setStatus(Status.PENDING);

		User saved = userRepo.save(u);
		reservationsRepo.save(r);

		return saved.getToken();
	}

}