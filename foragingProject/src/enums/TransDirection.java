package enums;

public enum TransDirection {
	FOOD_TO_NEST(1),
	NEST_TO_FOOD(2);

	private int code;

	private TransDirection(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
