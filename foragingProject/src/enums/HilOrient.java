 package enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.SubOrients;

public enum HilOrient {
	LEFT_DOWN(1, StartPos.BOTTOM_LEFT, new SubOrients(5, 1, 1, 4), new ArrayList<MoveCode>(Arrays.asList(MoveCode.PLUS_X, MoveCode.MINUS_Y, MoveCode.MINUS_X))),
	RIGHT_DOWN(2, StartPos.BOTTOM_RIGHT, new SubOrients(6, 2, 2, 3), new ArrayList<MoveCode>(Arrays.asList(MoveCode.MINUS_X, MoveCode.MINUS_Y, MoveCode.PLUS_X))),
	UP_LEFT(3, StartPos.TOP_LEFT, new SubOrients(7, 3, 3, 2), new ArrayList<MoveCode>(Arrays.asList(MoveCode.PLUS_Y, MoveCode.PLUS_X, MoveCode.MINUS_Y))),
	UP_RIGHT(4, StartPos.TOP_RIGHT, new SubOrients(8, 4, 4, 1), new ArrayList<MoveCode>(Arrays.asList(MoveCode.PLUS_Y, MoveCode.MINUS_X, MoveCode.MINUS_Y))),
	DOWN_LEFT(5, StartPos.BOTTOM_LEFT, new SubOrients(1, 5, 5, 8), new ArrayList<MoveCode>(Arrays.asList(MoveCode.MINUS_Y, MoveCode.PLUS_X, MoveCode.PLUS_Y))),
	DOWN_RIGHT(6, StartPos.BOTTOM_RIGHT, new SubOrients(2, 6, 6, 7), new ArrayList<MoveCode>(Arrays.asList(MoveCode.MINUS_Y, MoveCode.MINUS_X, MoveCode.PLUS_Y))),
	LEFT_UP(7, StartPos.TOP_LEFT, new SubOrients(3, 7, 7, 6), new ArrayList<MoveCode>(Arrays.asList(MoveCode.PLUS_X, MoveCode.PLUS_Y, MoveCode.MINUS_X))),
	RIGHT_UP(8, StartPos.TOP_RIGHT, new SubOrients(4, 8, 8, 5), new ArrayList<MoveCode>(Arrays.asList(MoveCode.MINUS_X, MoveCode.PLUS_Y, MoveCode.PLUS_X))),
	NULL(9);

	private int code;
	private StartPos start;
	private SubOrients subs;
	private List<MoveCode> mcode;

	private HilOrient(int code) {
		this.code = code;
	}

	private HilOrient(int code, StartPos start, SubOrients subs, List<MoveCode> mcode) {
		this.code = code;
		this.start = start;
		this.subs = subs;
		this.mcode = mcode;
	}

	public int getCode() {
		return code;
	}

	public StartPos getStart() {
		return start;
	}

	public static List<Integer> getSubs(int code) {
		return lookup.get(code).getSubs().getSubOrientList();
	}

	private SubOrients getSubs() {
		return subs;
	}

	public List<MoveCode> getMcode() {
		return mcode;
	}

	public HilOrient getOppOrientation() {
		return get(9 - this.getCode());
	}
	
	private static final Map<Integer, HilOrient> lookup = new HashMap<Integer, HilOrient>();

	static {
		for (HilOrient h : EnumSet.allOf(HilOrient.class))
			lookup.put(h.getCode(), h);
	}

	public static HilOrient get(int code) {
		return lookup.get(code);
	}
	
	
	public static List<MoveCode> getMCList(int code) {
        return lookup.get(code).getMcode();
    }
 
	

}
