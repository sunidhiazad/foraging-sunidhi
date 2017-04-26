package operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import entities.Cell;

public class Look {

	private Cell cell;
	private int hops;
	private List<Cell> neighbours;

	public Look(Cell cell, int hops) {
		this.cell = cell;
		this.hops = hops;
		neighbours = new ArrayList<Cell>();
	}

	public List<Cell> getNeighbouringCells() {

		for (int i = 0; i < hops; i++) {
			addLeftAndRightCells(Operations.plusY(cell, hops - i), i);
			addLeftAndRightCells(Operations.minusY(cell, hops - i), i);
		}

		addLeftAndRightCells(cell, hops);

		// remove the cell itself from it's neighbors list
		neighbours.removeAll(Collections.singleton(cell));

		// remove any duplicate cells from the list
		neighbours = new ArrayList<Cell>(new HashSet<Cell>(neighbours));

		return neighbours;
	}

	private void addLeftAndRightCells(Cell c, int hops) {
		if (hops > 0) {
			for (int i = 1; i <= hops; i++) {
				neighbours.add(Operations.plusX(c, i));
				neighbours.add(Operations.minusX(c, i));
			}
		}
		neighbours.add(c);
	}
	
	public static void main(String[] args) {
		System.out.println(new Look(new Cell(0,0),8).getNeighbouringCells().size());
	}

}
