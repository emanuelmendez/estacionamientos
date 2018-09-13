package model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "REVIEW")
public class Review {
	private long id;
	private Score score;
	private String comment;
	private Date dateReviewed;	
	private Reservation reservation;
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Enumerated
	public Score getScore() {
		return score;
	}
	public void setScore(Score score) {
		this.score = score;
	}
	
	@Column(name = "COMMENT")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Column(name = "DATE_REVIEWED")
	public Date getDateReviewed() {
		return dateReviewed;
	}
	public void setDateReviewed(Date dateReviewed) {
		this.dateReviewed = dateReviewed;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
}
