package enums;

public enum MoveCode {
	START(" S "),
	PLUS_X(" +X "),
	MINUS_X(" -X "),
	PLUS_Y(" +Y "),
	MINUS_Y(" -Y ");

	private String code;

	MoveCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
