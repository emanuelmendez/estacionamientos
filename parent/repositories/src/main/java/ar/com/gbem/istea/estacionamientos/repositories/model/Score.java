package ar.com.gbem.istea.estacionamientos.repositories.model;

public enum Score {

	NOT_REVIEWED(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

	private int value;

	private Score(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

	public static Score of(int score) {
		switch (score) {
		case 1:
			return ONE;
		case 2:
			return TWO;
		case 3:
			return THREE;
		case 4:
			return FOUR;
		case 5:
			return FIVE;
		default:
			return NOT_REVIEWED;
		}

	}
}