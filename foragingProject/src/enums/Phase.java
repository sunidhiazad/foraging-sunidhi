package enums;

public enum Phase {
	NULL(0, "NU"),
	INITIAL_DEPLOYMENT(1, "ID"),
	EXPLORATION(2, "EX"),
	COMMUNICATION(3, "CO"),
	TRANSPORTATION(4, "TR");

	private int code;
	private String alias;

	private Phase(int code, String alias) {
		this.code = code;
		this.alias = alias;
	}

	public int getCode() {
		return code;
	}

	public String getAlias() {
		return alias;
	}
}
