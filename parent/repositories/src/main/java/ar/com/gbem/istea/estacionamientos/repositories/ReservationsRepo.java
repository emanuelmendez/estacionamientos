package ar.com.gbem.istea.estacionamientos.repositories;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.gbem.istea.estacionamientos.repositories.model.Reservation;
import ar.com.gbem.istea.estacionamientos.repositories.model.Status;

@Repository
public interface ReservationsRepo extends CrudRepository<Reservation, Long> {

	@SuppressWarnings("all")
	Reservation findFirstByDriver_TokenAndStatusInAndToAfterOrderByFrom(String subject, EnumSet<Status> activeStatus,
			Date since);

	@Query("from Reservation r where r.driver.token = :subject and r.status in :status order by r.from")
	List<Reservation> getOfDriverBySubject(@Param("subject") String subject,
			@Param("status") EnumSet<Status> activeStatus);

	@Query("from Reservation r where r.lender.token = :subject and r.status in :status order by r.from desc")
	List<Reservation> getOfLenderBySubject(@Param("subject") String subject,
			@Param("status") EnumSet<Status> activeStatus);

	@Query("select count(*) from Reservation r where r.parkingLot.id = :parkingLotId and ("
			+ "(r.from >= :date_from and r.to <= :date_to) OR (r.from <= :date_from and r.to >= :date_to)"
			+ ") and r.status in :status order by r.from")
	long findOccupancy(@Param("parkingLotId") long parkingLotId, @Param("date_from") Date dateFrom,
			@Param("date_to") Date dateTo, @Param("status") EnumSet<Status> activeStatus);

	@Query("from Reservation r where r.driver.token = :subject and r.status = :status order by r.from desc")
	List<Reservation> getDoneOfDriverBySubject(@Param("subject") String subject, @Param("status") Status status);

	@Query("from Reservation r where r.status in :status and :now between r.from and r.to")
	List<Reservation> findAllApprovedStarted(@Param("status") EnumSet<Status> status, @Param("now") Date now);

	@Query("from Reservation r where r.status = :status and :now >= r.to")
	List<Reservation> findAllInProgressEnded(@Param("status") Status status, @Param("now") Date now);

}
