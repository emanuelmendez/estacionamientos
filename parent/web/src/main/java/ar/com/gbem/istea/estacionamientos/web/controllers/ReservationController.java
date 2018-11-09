package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gbem.istea.estacionamientos.core.services.ReservationsService;
import ar.com.gbem.istea.estacionamientos.web.Constants;
import ar.gob.gbem.istea.estacionamientos.dtos.ReservationDTO;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

	@Autowired
	private ReservationsService reservationsService;

	@RequestMapping(value = "/driver", method = RequestMethod.GET)
	public ResponseEntity<List<ReservationDTO>> getReservationsByDriverUser(HttpSession session) {
		String subject = (String) session.getAttribute(Constants.SUBJECT);

		List<ReservationDTO> reservations = reservationsService.getOfDriverBySubject(subject);
		if (reservations.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}

	@RequestMapping(value = "/driver/current", method = RequestMethod.GET)
	public ResponseEntity<ReservationDTO> getCurrentByDriverUser(HttpSession session) {
		String subject = (String) session.getAttribute(Constants.SUBJECT);

		ReservationDTO reservation = reservationsService.getCurrentOfDriverBySubject(subject);
		if (reservation == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(reservation, HttpStatus.OK);
	}

	@RequestMapping(value = "/driver/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> cancelCurrentReservation(HttpSession session, @PathVariable("id") Long id) {
		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/lender/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> rejectOrCancelLenderReservation(HttpSession session, @PathVariable("id") Long id) {
		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/lender/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> acceptLenderReservation(HttpSession session, @PathVariable("id") Long id) {
		if (id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/lender", method = RequestMethod.GET)
	public ResponseEntity<List<ReservationDTO>> getCurrentByLenderUser(HttpSession session) {
		String subject = (String) session.getAttribute(Constants.SUBJECT);

		List<ReservationDTO> reservations = reservationsService.getOfLenderBySubject(subject);
		if (reservations.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}

}
