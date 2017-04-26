package comparators;

import java.util.Comparator;

import entities.Sector;

public class SectorComparator2 implements Comparator<Sector> {

	@Override
	public int compare(Sector s1, Sector s2) {
		if (s1.getExpTimeStep() > s2.getExpTimeStep())
			return 1;
		else
			return -1;
	}

}
