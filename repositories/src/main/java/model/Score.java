package model;

public enum Score{
	ONE(1),
	TWO(2),
	THREE(3),
	FOUR(4),
	FIVE(5);
	
	private int value;

    private Score(int value) {
        this.value = value;
    }

	public int value() {
		return value;
	}
}