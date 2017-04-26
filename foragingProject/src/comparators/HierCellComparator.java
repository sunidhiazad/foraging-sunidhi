package comparators;

import java.util.Comparator;

import entities.Cell;

public class HierCellComparator implements Comparator<Cell> {

	@Override
	public int compare(Cell cell1, Cell cell2) {
		if (cell1.getY() == cell2.getY()) {
			if (cell1.getX() < cell2.getX())
				return -1;
			else
				return 1;
		}
		if (cell1.getX() == cell2.getX()) {
			if (cell1.getY() < cell2.getY())
				return -1;
			else
				return 1;
		}
		if (cell1.getY() < cell2.getY())
			return -1;
		else
			return 1;
	}

}
