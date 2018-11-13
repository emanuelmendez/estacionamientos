package ar.com.gbem.istea.estacionamientos.core.exceptions;

public class ReservationConflictException extends Exception {

	private static final long serialVersionUID = 2980024764640579128L;

	public ReservationConflictException(String message) {
		super(message);
	}

}
