package operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entities.Cell;

public class FoodLocations {

	private int from;
	private int to;
	private int count;
	private Cell origin;
	private Map<Cell, Integer> map = new HashMap<Cell, Integer>();

	public FoodLocations(int gridSize, int from, int to, int count) {
		this.origin = Operations.getOrigin(gridSize);
		this.from = from * Operations.getDistance(new Cell(0, 0), origin) / 100;
		this.to = to * Operations.getDistance(new Cell(0, 0), origin) / 100;
		this.count = count;
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				map.put(new Cell(i, j), Operations.getDistance(new Cell(i, j), origin));
			}
		}
	}

	public List<Cell> get() {
		List<Cell> eligibleCells = new ArrayList<Cell>();
		for (Map.Entry<Cell, Integer> entry : map.entrySet()) {
			if (entry.getValue() >= from && entry.getValue() < to)
				eligibleCells.add(entry.getKey());
		}
		return pickNRandom(eligibleCells, count);
	}

	public List<Cell> pickNRandom(List<Cell> lst, int n) {
		List<Cell> copy = new LinkedList<Cell>(lst);
		Collections.shuffle(copy);
		return copy.subList(0, n);
	}

	public static void main(String[] args) {
		List<Cell> foods = new FoodLocations(128, 15, 35, 20).get();
		for (Cell c : foods) {
			System.out.println(c.getX() + " " + c.getY());
		}
	}
}
