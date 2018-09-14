package ar.com.gbem.istea.estacionamientos.repositories.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REVIEW")
public class Review {

	@Id
	@GeneratedValue
	private long id;

	@Enumerated
	private Score score;

	private String comment;

	@Column(name = "DATE_REVIEWED")
	private Date dateReviewed;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID")
	private Reservation reservation;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDateReviewed() {
		return dateReviewed;
	}

	public void setDateReviewed(Date dateReviewed) {
		this.dateReviewed = dateReviewed;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((reservation == null) ? 0 : reservation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Review other = (Review) obj;
		if (id != other.id) {
			return false;
		}
		if (reservation == null) {
			if (other.reservation != null) {
				return false;
			}
		} else if (!reservation.equals(other.reservation)) {
			return false;
		}
		return true;
	}

}
