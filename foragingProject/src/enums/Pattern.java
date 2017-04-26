package enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Pattern {
	NULL(0),
	STRIPES(1),
	ZIG_ZAG(2),
	HILBERT(3),
	SECTORS(4),
	LOPEZ_ORTIZ4(5),
	LOPEZ_ORTIZ8(6),
	ADVANCED_SECTORS(7),
	LOPEZ_ORTIZ1(6),
	LOPEZ_ORTIZ16(8);
	private int code;

	private Pattern(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	private static final Map<Integer, Pattern> lookup = new HashMap<Integer, Pattern>();

	static {
		for (Pattern h : EnumSet.allOf(Pattern.class))
			lookup.put(h.getCode(), h);
	}

	public static Pattern get(int code) {
		return lookup.get(code);
	}
}
