package ar.com.gbem.istea.estacionamientos.repositories;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ar.com.gbem.istea.estacionamientos.repositories.model.Reservation;
import ar.com.gbem.istea.estacionamientos.repositories.model.Status;

public interface ReservationsRepo extends CrudRepository<Reservation, Long> {

	Reservation findFirstByDriver_TokenAndStatusInAndToAfterOrderByFrom(String subject, EnumSet<Status> activeStatus, Date since);

	@Query("from Reservation r where r.driver.token = :subject and r.status in :status order by r.from")
	List<Reservation> getOfDriverBySubject(@Param("subject") String subject, @Param("status") EnumSet<Status> activeStatus);

	@Query("from Reservation r where r.lender.token = :subject and r.status in :status order by r.from")
	List<Reservation> getOfLenderBySubject(@Param("subject") String subject, @Param("status") EnumSet<Status> activeStatus);

	
}
