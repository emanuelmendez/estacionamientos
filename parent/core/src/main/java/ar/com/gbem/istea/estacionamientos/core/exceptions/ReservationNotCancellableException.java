package ar.com.gbem.istea.estacionamientos.core.exceptions;

public class ReservationNotCancellableException extends Exception {

	private static final long serialVersionUID = -4491694865629338601L;

	public ReservationNotCancellableException(String message) {
		super(message);
	}

}
