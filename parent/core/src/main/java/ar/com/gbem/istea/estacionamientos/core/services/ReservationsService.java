package ar.com.gbem.istea.estacionamientos.core.services;

import java.util.Date;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gbem.istea.estacionamientos.core.DozerUtil;
import ar.com.gbem.istea.estacionamientos.core.exceptions.ReservationConflictException;
import ar.com.gbem.istea.estacionamientos.core.exceptions.ReservationNotCancellableException;
import ar.com.gbem.istea.estacionamientos.core.exceptions.ReservationNotConfirmableException;
import ar.com.gbem.istea.estacionamientos.repositories.ParkingLotsRepo;
import ar.com.gbem.istea.estacionamientos.repositories.ReservationsRepo;
import ar.com.gbem.istea.estacionamientos.repositories.ReviewRepo;
import ar.com.gbem.istea.estacionamientos.repositories.UserRepository;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;
import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLotSolr;
import ar.com.gbem.istea.estacionamientos.repositories.model.Reservation;
import ar.com.gbem.istea.estacionamientos.repositories.model.Review;
import ar.com.gbem.istea.estacionamientos.repositories.model.Score;
import ar.com.gbem.istea.estacionamientos.repositories.model.Status;
import ar.com.gbem.istea.estacionamientos.repositories.model.User;
import ar.gob.gbem.istea.estacionamientos.dtos.ReservationDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.ReservationOptionsDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.ReviewDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.SearchDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.UserResultDTO;

@Service
public class ReservationsService {

	@Autowired
	private ReservationsRepo reservationsRepo;

	@Autowired
	private ReviewRepo reviewRepo;

	@Autowired
	private ParkingLotsRepo parkingLotRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private DozerUtil mapper;

	private static final Logger logger = LoggerFactory.getLogger(ReservationsService.class);

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
		List<Reservation> reservations = reservationsRepo.getOfLenderBySubject(subject, EnumSet.allOf(Status.class));
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

		notificationService.send("Nueva solicitud pendiente",
				String.format("¡%s está esperando que le confirmes!", driver.getName()),
				r.getLender().getDeviceToken());
	}

	@Transactional
	public void cancelCurrentReservation(long id) throws ReservationNotCancellableException {
		Reservation r = reservationsRepo.findById(id).orElseThrow(IllegalArgumentException::new);

		// TODO columna de modificacion
		if (ACTIVE_STATUS.contains(r.getStatus())) {
			r.setStatus(Status.CANCELLED);
			reservationsRepo.save(r);
			notificationService.send("Se canceló tu reserva", "", r.getDriver().getDeviceToken());
		} else {
			throw new ReservationNotCancellableException("Reservation has status: " + r.getStatus().description());
		}
	}

	@Transactional
	public void confirmReservation(long id) throws ReservationNotConfirmableException {
		Reservation r = reservationsRepo.findById(id).orElseThrow(IllegalArgumentException::new);

		if (!Status.PENDING.equals(r.getStatus())) {
			throw new ReservationNotConfirmableException("Reservation has status: " + r.getStatus().description());
		} else if (reservationsRepo.findOccupancy(id, r.getFrom(), r.getTo(), ACTIVE_STATUS) > 0) {
			r.setStatus(Status.CANCELLED);
			reservationsRepo.save(r);
			notificationService.send("Se canceló tu reserva", "", r.getDriver().getDeviceToken());
			throw new ReservationNotConfirmableException(
					"Parking lot isn't available now. Reservation cancelled, id: " + id);
		} else {
			r.setStatus(Status.APPROVED);
			reservationsRepo.save(r);
			notificationService.send("Se confirmó tu reserva", r.getLender().getName() + " aprobó tu solicitud",
					r.getDriver().getDeviceToken());
		}

	}

	@Transactional(readOnly = true)
	public List<ReservationDTO> getPendingOfLenderBySubject(String subject) {
		List<Reservation> reservations = reservationsRepo.getOfLenderBySubject(subject, EnumSet.of(Status.PENDING));
		return mapper.getReservationsFrom(reservations);
	}

	@Transactional
	public void updateReservationsStatus() {
		final Date now = new Date();
		List<Reservation> approvedOrPending = reservationsRepo
				.findAllApprovedStarted(EnumSet.of(Status.PENDING, Status.APPROVED), now);
		for (Reservation reservation : approvedOrPending) {
			if (reservation.getStatus().equals(Status.APPROVED)) {
				reservation.setStatus(Status.IN_PROGRESS);
			} else {
				reservation.setStatus(Status.CANCELLED);
			}
		}
		reservationsRepo.saveAll(approvedOrPending);

		if (logger.isInfoEnabled()) {
			logger.info(String.format("Se cambió de estado a las reservas pendientes y aprobadas: %d",
					approvedOrPending.size()));
		}

		for (Reservation reservation : approvedOrPending) {
			notificationService.send("¡Comenzó tu reserva!", "Volvé por tu vehículo más tarde",
					reservation.getDriver().getDeviceToken());
			notificationService.send(String.format("Comienza la reserva de %s", reservation.getDriver().getName()),
					"Te avisaremos si te cancela la reserva", reservation.getLender().getDeviceToken());
		}

		List<Reservation> inProgress = reservationsRepo.findAllInProgressEnded(Status.IN_PROGRESS, now);
		for (Reservation reservation : inProgress) {
			reservation.setStatus(Status.DONE);
		}
		reservationsRepo.saveAll(inProgress);

		for (Reservation reservation : inProgress) {
			notificationService.send("¡Terminó tu reserva!", "No te olvides de calificar al usuario",
					reservation.getDriver().getDeviceToken());
			notificationService.send(String.format("Fin de la reserva de %s", reservation.getDriver().getName()),
					String.format("Ganaste $ %.2f", reservation.getValue().doubleValue()),
					reservation.getLender().getDeviceToken());
		}

		if (logger.isInfoEnabled()) {
			logger.info(String.format("Se cambió de estado a las reservas en curso: %d", inProgress.size()));
		}

	}

	@Transactional(readOnly = true)
	public List<ReservationDTO> getReservationHistoryBySubject(String subject) {
		List<Reservation> reservations = reservationsRepo.getDoneOfDriverBySubject(subject, Status.DONE);
		return mapper.getReservationsFrom(reservations);
	}

	@Transactional
	public void postReview(final long id, final ReviewDTO reviewDTO) {
		Reservation reservation = reservationsRepo.findById(id).orElseThrow(IllegalArgumentException::new);

		if (reservation.getReview() != null) {
			reviewRepo.delete(reservation.getReview());
			reservation.setReview(null);
		}

		Review review = new Review();
		review.setDateReviewed(new Date());
		review.setComment(reviewDTO.getComment());
		review.setScore(Score.of(reviewDTO.getScore()));
		reservation.setReview(review);
		review.setReservation(reservation);

	}
	
	@Transactional(readOnly = true)
	public Float getAverageScoreByLender(String subject) {
		User lender = userRepo.getByToken(subject);
		return reservationsRepo.averageScoreByLender(lender.getId());
	}

}
