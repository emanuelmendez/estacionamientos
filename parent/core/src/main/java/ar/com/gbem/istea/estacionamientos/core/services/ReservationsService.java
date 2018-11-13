package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gbem.istea.estacionamientos.core.DozerUtil;
import ar.com.gbem.istea.estacionamientos.core.exceptions.ReservationConflictException;
import ar.com.gbem.istea.estacionamientos.repositories.ParkingLotsRepo;
import ar.com.gbem.istea.estacionamientos.repositories.ReservationsRepo;
import ar.com.gbem.istea.estacionamientos.repositories.UserRepository;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLotSolr;
import ar.com.gbem.istea.estacionamientos.repositories.model.Reservation;
import ar.com.gbem.istea.estacionamientos.repositories.model.Status;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.gob.gbem.istea.estacionamientos.dtos.ReservationDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.ReservationOptionsDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.SearchDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@Service
public class ReservationsService {

	@Autowired
	private ReservationsRepo reservationsRepo;

	@Autowired
	private ParkingLotsRepo parkingLotRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private DozerUtil mapper;

	private static final EnumSet<Status> ACTIVE_STATUS = EnumSet.of(Status.IN_PROGRESS, Status.APPROVED,
			Status.PENDING);

	@Transactional(readOnly = true)
	public ReservationDTO getCurrentOfDriverBySubject(String subject) {
		Reservation reservation = reservationsRepo.findFirstByDriver_TokenAndStatusInAndToAfterOrderByFrom(subject,
				ACTIVE_STATUS, new Date());
		if (reservation == null)
			return null;
		return mapper.getReservationFrom(reservation);
	}

	@Transactional(readOnly = true)
	public List<ReservationDTO> getOfDriverBySubject(String subject) {
		List<Reservation> reservations = reservationsRepo.getOfDriverBySubject(subject, ACTIVE_STATUS);
		return mapper.getReservationsFrom(reservations);
	}

	@Transactional(readOnly = true)
	public List<ReservationDTO> getOfLenderBySubject(String subject) {
		List<Reservation> reservations = reservationsRepo.getOfLenderBySubject(subject, ACTIVE_STATUS);
		return mapper.getReservationsFrom(reservations);
	}

	public void retainByAvailability(List<ParkingLotSolr> results, SearchDTO dto) {
		for (Iterator<ParkingLotSolr> it = results.iterator(); it.hasNext();) {
			ParkingLotSolr parkingLot = it.next();
			if (reservationsRepo.findOccupancy(parkingLot.getId(), dto.getFromDate(), dto.getToDate(),
					ACTIVE_STATUS) > 0) {
				it.remove();
			}
		}
	}

	@Transactional
	public void postReservation(UserResultDTO driverDTO, ReservationOptionsDTO dto)
			throws ReservationConflictException {
		ParkingLot parkingLot = parkingLotRepo.findById(dto.getParkingLotId())
				.orElseThrow(IllegalArgumentException::new);

		if (reservationsRepo.findOccupancy(dto.getParkingLotId(), dto.getDateFrom(), dto.getDateTo(),
				ACTIVE_STATUS) > 0) {
			throw new ReservationConflictException("Parking lot has occupancy");
		}

		User driver = userRepo.findById(driverDTO.getId()).orElseThrow(IllegalArgumentException::new);

		Reservation r = new Reservation();
		r.setDriver(driver);
		r.setFrom(dto.getDateFrom());
		r.setTo(dto.getDateTo());
		r.setLender(parkingLot.getUser());
		r.setParkingLot(parkingLot);
		r.setStatus(Status.PENDING);
		r.setVehicle(driver.getVehicles().get(0));
		r.setValue(parkingLot.getValue());

		reservationsRepo.save(r);
	}

}
