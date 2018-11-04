package ar.com.gbem.istea.estacionamientos.repositories.model;

public enum Status {
	PENDING(0, "Pending"), 
	APPROVED(1, "Approved"), 
	IN_PROGRESS(2, "In Progress"), 
	DONE(3, "Done"), 
	CANCELLED(4, "Cancelled");

	private int key;
	private String description;

	private Status(int key, String description) {
		this.key = key;
		this.description = description;
	}

	public int key() {
		return key;
	}

	public String description() {
		return description;
	}
	
	@Override
	public String toString() {
		return description;
	}

}
