package comparators;

import java.util.Comparator;

import entities.Cell;
import entities.Sector;

// 1 means s1 should come out before s2.
public class SectorComparator implements Comparator<Sector> {

	// @Override
	// public int compare(Sector s1, Sector s2) {
	// if (s1.getDepDistance() == s2.getDepDistance()) {
	// if (s1.getId() < s2.getId())
	// return 1;
	// else
	// return -1;
	// } else {
	// if (s1.getDepDistance() > s2.getDepDistance())
	// return 1;
	// else
	// return -1;
	// }
	// }
	@Override
	public int compare(Sector s1, Sector s2) {
		Cell fp1 = s1.getFirstPoint();
		Cell fp2 = s2.getFirstPoint();
		if ((s1.getQuadCode() == 11 && s2.getQuadCode() == 11) || (s1.getQuadCode() == 42 && s2.getQuadCode() == 42)) {
			if (fp1.getX() == fp2.getX()) {
				if (s1.getDepDistance() > s2.getDepDistance())
					return 1;
				else
					return -1;
			} else {
				if (fp1.getX() > fp2.getX())
					return 1;
				else
					return -1;
			}
		} else if ((s1.getQuadCode() == 12 && s2.getQuadCode() == 12) || (s1.getQuadCode() == 21 && s2.getQuadCode() == 21)) {
			if (fp1.getY() == fp2.getY()) {
				if (s1.getDepDistance() > s2.getDepDistance())
					return 1;
				else
					return -1;
			} else {
				if (fp1.getY() < fp2.getY())
					return 1;
				else
					return -1;
			}
		} else if ((s1.getQuadCode() == 22 && s2.getQuadCode() == 22) || (s1.getQuadCode() == 31 && s2.getQuadCode() == 31)) {
			if (fp1.getX() == fp2.getX()) {
				if (s1.getDepDistance() > s2.getDepDistance())
					return 1;
				else
					return -1;
			} else {
				if (fp1.getX() < fp2.getX())
					return 1;
				else
					return -1;
			}
		} else if ((s1.getQuadCode() == 32 && s2.getQuadCode() == 32) || (s1.getQuadCode() == 41 && s2.getQuadCode() == 41)) {
			if (fp1.getY() == fp2.getY()) {
				if (s1.getDepDistance() > s2.getDepDistance())
					return 1;
				else
					return -1;
			} else {
				if (fp1.getY() > fp2.getY())
					return 1;
				else
					return -1;
			}
		}
		return 0;
	}

}
