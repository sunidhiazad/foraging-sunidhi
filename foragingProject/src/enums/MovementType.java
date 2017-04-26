package enums;

public enum MovementType {
	DELTA_X(0),
	DELTA_Y(1),
	MAX_DELTA(2),
	RANDOM(3),
	FREE(4);

	private int code;

	private MovementType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
