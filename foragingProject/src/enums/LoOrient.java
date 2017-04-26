package enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum LoOrient {
	LEFT_UP(2, MoveCode.MINUS_X, MoveCode.MINUS_Y),
	RIGHT_DOWN(4, MoveCode.PLUS_X, MoveCode.PLUS_Y),
	RIGHT_UP(1, MoveCode.PLUS_X, MoveCode.MINUS_Y),
	LEFT_DOWN(3, MoveCode.MINUS_X, MoveCode.PLUS_Y),
	UP_LEFT(6, MoveCode.MINUS_Y, MoveCode.MINUS_X),
	DOWN_RIGHT(8, MoveCode.PLUS_Y, MoveCode.PLUS_X),
	UP_RIGHT(5, MoveCode.MINUS_Y, MoveCode.PLUS_X),
	DOWN_LEFT(7, MoveCode.PLUS_Y, MoveCode.MINUS_X);

	private int code;
	private MoveCode first;
	private MoveCode second;
	private static final Map<Integer, LoOrient> lookup = new HashMap<Integer, LoOrient>();

	private LoOrient(int code, MoveCode first, MoveCode second) {
		this.code = code;
		this.first = first;
		this.second = second;
	}

	static {
		for (LoOrient lo : EnumSet.allOf(LoOrient.class))
			lookup.put(lo.getCode(), lo);
	}

	public int getCode() {
		return code;
	}

	public static LoOrient get(int code) {
		return lookup.get(code);
	}

	public MoveCode getFirst() {
		return first;
	}

	public MoveCode getSecond() {
		return second;
	}

}
