package ar.com.gbem.istea.estacionamientos.web.controllers;

import java.io.Serializable;

public class TestDataDTO implements Serializable {

	public TestDataDTO() {
		super();
	}

	private static final long serialVersionUID = 1613873150212784758L;

	String mail;
	String streetAdress;
	double longitude;
	double latitude;
	String from;
	String to;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getStreetAdress() {
		return streetAdress;
	}

	public void setStreetAdress(String streetAdress) {
		this.streetAdress = streetAdress;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}