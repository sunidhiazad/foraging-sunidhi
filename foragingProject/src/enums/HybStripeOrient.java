package enums;

import java.util.Random;

public enum HybStripeOrient {
	NULL,
	HORIZONTAL_UPPER_LEFT,
	HORIZONTAL_LOWER_LEFT,
	VERTICAL_UPPER_LEFT,
	VERTICAL_LOWER_LEFT,
	HORIZONTAL_UPPER_RIGHT,
	HORIZONTAL_LOWER_RIGHT,
	VERTICAL_UPPER_RIGHT,
	VERTICAL_LOWER_RIGHT;

	public static HybStripeOrient getRandomOrientByQuad(int quad) {
		switch (quad) {
		case 1:
			if (new Random().nextBoolean())
				return HORIZONTAL_UPPER_RIGHT;
			else
				return VERTICAL_UPPER_RIGHT;
		case 2:
			if (new Random().nextBoolean())
				return HORIZONTAL_UPPER_LEFT;
			else
				return VERTICAL_UPPER_LEFT;
		case 3:
			if (new Random().nextBoolean())
				return HORIZONTAL_LOWER_LEFT;
			else
				return VERTICAL_LOWER_LEFT;
		case 4:
			if (new Random().nextBoolean())
				return HORIZONTAL_LOWER_RIGHT;
			else
				return VERTICAL_LOWER_RIGHT;
		}
		return NULL;
	}
}
