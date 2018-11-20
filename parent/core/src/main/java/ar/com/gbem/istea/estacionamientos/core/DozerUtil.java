package ar.com.gbem.istea.estacionamientos.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import ar.com.gbem.istea.estacionamientos.repositories.model.ParkingLot;
import ar.com.gbem.istea.estacionamientos.repositories.model.Reservation;
import ar.gob.gbem.istea.estacionamientos.dtos.ParkingLotResultDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.ReservationDTO;
import ar.gob.gbem.istea.estacionamientos.dtos.ReviewDTO;

/**
 * Bean utilizado para instanciar objetos a partir de los valores de otros
 * 
 * @author pielreloj
 *
 */
@Service("mapper")
public final class DozerUtil {

	private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

	/**
	 * Instancia un objeto de clase B a partir de un objeto de clase A, copiando
	 * todos los atributos con el mismo nombre, utilizando Dozer
	 * 
	 * @param src              Instancia del objeto a copiar
	 * @param destinationClass clase de la instancia del objeto que se obtendrá
	 * @return una instancia de un objeto de clase B con los valores de los
	 *         atributos que existan en ambas clases
	 */
	public final <B, A> B map(final A src, final Class<? extends B> destinationClass) {
		return mapper.map(src, destinationClass);
	}

	/**
	 * Instancia una lista de objetos de clase B a partir de una lista de objetos de
	 * clase A, copiando todos los atributos con el mismo nombre de cada uno,
	 * utilizando Dozer
	 * 
	 * @param src              lista de instancias del objeto a copiar
	 * @param destinationClass clase de la instancia del objeto que se obtendrá en
	 *                         la lista
	 * @return una instancia de una lista de objetos de clase B con los valores de
	 *         los atributos que existan en ambas clases
	 */
	public final <B, A> List<B> map(final Iterable<? extends A> src, final Class<? extends B> destinationClass) {
		final List<B> list = new ArrayList<>();

		for (final A a : src)
			list.add(mapper.map(a, destinationClass));

		return list;
	}

	public final ReservationDTO getReservationFrom(final Reservation source) {
		final ReservationDTO destination = new ReservationDTO();
		destination.setId(source.getId());
		destination.setDriverName(source.getDriver().getName());
		destination.setLenderName(source.getLender().getName());
		destination.setVehicleDescription(source.getVehicle().toString());
		destination.setParkingLot(getParkingLotFrom(source.getParkingLot()));
		destination.setFrom(source.getFrom());
		destination.setTo(source.getTo());
		destination.setStatus(source.getStatus().description());
		destination.setValue(source.getValue().doubleValue());
		if (source.getReview() != null) {
			destination.setReview(map(source.getReview(), ReviewDTO.class));
			destination.getReview().setScore(source.getReview().getScore().value());
		}
		return destination;
	}

	public final List<ReservationDTO> getReservationsFrom(final Iterable<? extends Reservation> sourceList) {
		List<ReservationDTO> dtoList = new ArrayList<>();

		for (Reservation source : sourceList)
			dtoList.add(getReservationFrom(source));

		return dtoList;
	}

	public final ParkingLotResultDTO getParkingLotFrom(final ParkingLot source) {
		final ParkingLotResultDTO destination = map(source, ParkingLotResultDTO.class);
		destination.setAddressId(source.getAddress().getId());
		destination.setCoordinates(Double.toString(source.getAddress().getLatitude()) + ","
				+ Double.toString(source.getAddress().getLongitude()));
		destination.setStreetAddress(source.getAddress().getStreetAddress());
		destination.setUserFullName(source.getUser().getName() + " " + source.getUser().getSurname());
		destination.setValue(source.getValue().doubleValue());

		return destination;
	}

}
