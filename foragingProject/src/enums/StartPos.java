package enums;

public enum StartPos {
	TOP_LEFT(1),
	TOP_RIGHT(2),
	BOTTOM_LEFT(3),
	BOTTOM_RIGHT(4);

	private int code;

	private StartPos(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
