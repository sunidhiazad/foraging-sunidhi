package comparators;

import java.util.Comparator;

import noCollisionProof.Cagent;

public class CagentComparator implements Comparator<Cagent> {

	@Override
	public int compare(Cagent ca1, Cagent ca2) {
		if (ca1.getDistance() == ca2.getDistance()) {
			if (ca1.getId() < ca2.getId())
				return 1;
			else
				return -1;
		} else {
			if (ca1.getDistance() > ca2.getDistance())
				return 1;
			else
				return -1;
		}
	}

}
