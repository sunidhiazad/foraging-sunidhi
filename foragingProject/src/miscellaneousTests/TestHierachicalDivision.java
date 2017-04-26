package miscellaneousTests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.GeneralOperations;
import operations.Operations;
import entities.Cell;

//class to test start points of hierarchial division
public class TestHierachicalDivision {

	private int gridSize;
	private int numAgents;
	private List<Cell> depPositions;

	private Map<Cell, Integer> map1 = new HashMap<Cell, Integer>();
	private Map<Cell, Integer> map2 = new HashMap<Cell, Integer>();
	private Map<Cell, Integer> map3 = new HashMap<Cell, Integer>();
	private Map<Cell, Integer> map4 = new HashMap<Cell, Integer>();

	public TestHierachicalDivision(int gridSize, int numAgents) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		depPositions = getDeploymentPositions();
		prepareMap();
	}

	private void prepareMap() {
		int part = numAgents / 4;
		Cell origin1 = new Cell(gridSize / 2 - 1, gridSize / 2 - 1);
		Cell ex1 = new Cell(0, origin1.getY());
		Cell ex2 = new Cell(gridSize - 1, origin1.getY());
		Cell ex3 = Operations.plusY(ex1, 1);
		Cell ex4 = Operations.plusY(ex2, 1);
		for (int i = 0; i < depPositions.size(); i++) {
			if (i >= 0 && i < part) {
				map1.put(depPositions.get(i), Operations.getDistance(depPositions.get(i), ex1) + gridSize / 2 - 1);
			} else if (i >= part && i < 2 * part) {
				map2.put(depPositions.get(i), Operations.getDistance(depPositions.get(i), ex2) + gridSize / 2 - 1);
			} else if (i >= 2 * part && i < 3 * part) {
				map3.put(depPositions.get(i), Operations.getDistance(depPositions.get(i), ex3) + gridSize / 2 - 1);
			} else if (i >= 3 * part) {
				map4.put(depPositions.get(i), Operations.getDistance(depPositions.get(i), ex4) + gridSize / 2 - 1);
			}
		}
		GeneralOperations.printMap(map1);
		GeneralOperations.printMap(map2);
		GeneralOperations.printMap(map3);
		GeneralOperations.printMap(map4);

	}

	private List<Cell> getDeploymentPositions() {
		List<Cell> lst = new ArrayList<Cell>();
		int size = (int) (gridSize / Math.sqrt(numAgents));
		int k = 0;

		for (int col = size - 1; col < gridSize; col = col + 2 * size) {
			for (int row = size - 1; row < gridSize; row = row + 2 * size) {
				Cell c = new Cell(row, col);
				lst.add(c);
				k++;
				if (k >= numAgents)
					break;
				c = Operations.plusX(c, 1);
				lst.add(c);
				k++;
				if (k >= numAgents)
					break;
				c = Operations.plusY(c, 1);
				lst.add(c);
				k++;
				if (k >= numAgents)
					break;
				c = Operations.minusX(c, 1);
				lst.add(c);
				k++;
				if (k >= numAgents)
					break;
			}
		}
		return lst;
	}

	public static void main(String[] args) {
		new TestHierachicalDivision(128, 64);

	}
}
