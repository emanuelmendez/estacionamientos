package ar.gob.gbem.istea.estacionamientos.dtos;

import java.io.Serializable;
import java.util.Date;

public class UserResultDTO implements Serializable {

	private static final long serialVersionUID = 8633041204967052854L;
	private long id;
	private String username;
	private String email;
	private String phone;
	private String name;
	private String surname;
	private Date since;
	private Date lastUpdated;
	private boolean active;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "UserResultDTO [id=" + id + ", username=" + username + ", email=" + email + ", phone=" + phone
				+ ", name=" + name + ", surname=" + surname + ", since=" + since + ", lastUpdated=" + lastUpdated
				+ ", active=" + active + "]";
	}

}