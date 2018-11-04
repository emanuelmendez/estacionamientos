package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gbem.istea.estacionamientos.core.DozerUtil;
import ar.com.gbem.istea.estacionamientos.repositories.ReservationsRepo;
import ar.com.gbem.istea.estacionamientos.repositories.model.Reservation;
import ar.com.gbem.istea.estacionamientos.repositories.model.Status;
import ar.gob.gbem.istea.estacionamientos.dtos.ReservationDTO;

@Service
public class ReservationsService {

	@Autowired
	private ReservationsRepo reservationsRepo;

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

}
