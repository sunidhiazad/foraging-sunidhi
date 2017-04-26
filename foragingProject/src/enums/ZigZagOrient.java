package enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ZigZagOrient {

	LEFT_DOWN(1, StartPos.BOTTOM_LEFT, MoveCode.PLUS_X, MoveCode.MINUS_Y, getMcArrayByCode(1)),
	RIGHT_DOWN(2, StartPos.BOTTOM_RIGHT, MoveCode.MINUS_X, MoveCode.MINUS_Y, getMcArrayByCode(2)),
	UP_LEFT(3, StartPos.TOP_LEFT, MoveCode.PLUS_Y, MoveCode.PLUS_X, getMcArrayByCode(3)),
	UP_RIGHT(4, StartPos.TOP_RIGHT, MoveCode.PLUS_Y, MoveCode.MINUS_X, getMcArrayByCode(4)),
	DOWN_LEFT(5, StartPos.BOTTOM_LEFT, MoveCode.MINUS_Y, MoveCode.PLUS_X, getMcArrayByCode(5)),
	DOWN_RIGHT(6, StartPos.BOTTOM_RIGHT, MoveCode.MINUS_Y, MoveCode.MINUS_X, getMcArrayByCode(6)),
	LEFT_UP(7, StartPos.TOP_LEFT, MoveCode.PLUS_X, MoveCode.PLUS_Y, getMcArrayByCode(7)),
	RIGHT_UP(8, StartPos.TOP_RIGHT, MoveCode.MINUS_X, MoveCode.PLUS_Y, getMcArrayByCode(8)),
	NULL(9);

	private int code;
	private StartPos start;
	private MoveCode oddTransMCode;
	private MoveCode evenTransMCode;
	private MoveCode[][] mcArray;
	
	private ZigZagOrient(int code) {
		this.code = code;
	}

	private ZigZagOrient(int code, StartPos start, MoveCode oddTransMCode, MoveCode evenTransMCode, MoveCode[][] mcArray) {
		this.code = code;
		this.start = start;
		this.oddTransMCode = oddTransMCode;
		this.evenTransMCode = evenTransMCode;
		this.mcArray = mcArray;
	}

	private static MoveCode[][] getMcArrayByCode(int code) {
		switch (code) {
		case 1:
			return new MoveCode[][]{ { MoveCode.MINUS_Y, MoveCode.MINUS_X }, { MoveCode.PLUS_X, MoveCode.PLUS_Y } };
		case 2:
			return new MoveCode[][]{ { MoveCode.MINUS_Y, MoveCode.PLUS_X }, { MoveCode.MINUS_X, MoveCode.PLUS_Y } };
		case 3:
			return new MoveCode[][]{ { MoveCode.PLUS_X, MoveCode.MINUS_Y }, { MoveCode.PLUS_Y, MoveCode.MINUS_X } };
		case 4:
			return new MoveCode[][]{ { MoveCode.MINUS_X, MoveCode.MINUS_Y }, { MoveCode.PLUS_Y, MoveCode.PLUS_X } };
		case 5:
			return new MoveCode[][]{ { MoveCode.PLUS_X, MoveCode.PLUS_Y }, { MoveCode.MINUS_Y, MoveCode.MINUS_X } };
		case 6:
			return new MoveCode[][]{ { MoveCode.MINUS_X, MoveCode.PLUS_Y }, { MoveCode.MINUS_Y, MoveCode.PLUS_X } };
		case 7:
			return new MoveCode[][]{ { MoveCode.PLUS_Y, MoveCode.MINUS_X }, { MoveCode.PLUS_X, MoveCode.MINUS_Y } };
		case 8:
			return new MoveCode[][]{ { MoveCode.PLUS_Y, MoveCode.PLUS_X }, { MoveCode.MINUS_X, MoveCode.MINUS_Y } };
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public StartPos getStart() {
		return start;
	}

	public MoveCode getOddTransMCode() {
		return oddTransMCode;
	}

	public MoveCode getEvenTransMCode() {
		return evenTransMCode;
	}

	public MoveCode[][] getMcArray() {
		return mcArray;
	}

	private static final Map<Integer, ZigZagOrient> lookup = new HashMap<Integer, ZigZagOrient>();

	static {
		for (ZigZagOrient h : EnumSet.allOf(ZigZagOrient.class))
			lookup.put(h.getCode(), h);
	}

	public static ZigZagOrient get(int code) {
		return lookup.get(code);
	}

}
