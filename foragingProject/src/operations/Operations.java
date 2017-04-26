package operations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import entities.Cell;
import entities.HierDividedArea;
import entities.Path;
import entities.Sector;
import enums.HilOrient;
import enums.MoveCode;
import enums.StartPos;
import enums.ZigZagOrient;

public class Operations {

	public static <T> void displayCollection(Collection<T> list) {
		System.out.println("***** PRINTING LIST *****");
		for (T item : list) {
			System.out.println(item);
		}
	}

	public static Boolean areNeighbours(Cell first, Cell second) {
		return getDistance(first, second) == 1;
	}

	public static Cell getOrigin(int gridSize) {
		return new Cell(gridSize / 2, gridSize / 2);
	}

	public static void displayPathWithDistance(List<Cell> lstCells) {
		Cell first = null;
		Cell second = null;
		System.out.println("***** SECTOR *****");
		for (int i = 0; i < lstCells.size(); i++) {
			first = lstCells.get(i);
			if (i < lstCells.size() - 1)
				second = lstCells.get(i + 1);
			else
				second = first;
			System.out.println(first + " : " + getDistance(first, second));
		}
	}

	public static BigDecimal getStdDeviation(List<Sector> sectors) {
		List<Integer> list = new ArrayList<Integer>();
		for (Sector s : sectors) {
			list.add(s.getFinalPath().size());
		}
		return GeneralOperations.getDeviation(list);
	}

	public static BigDecimal getAverage(List<Sector> sec) {
		List<Integer> list = new ArrayList<Integer>();
		for (Sector s : sec) {
			list.add(s.getFinalPath().size());
		}
		return GeneralOperations.getAverage(list);
	}

	public static BigDecimal findAngle(Cell cell, Cell origin) {
		int dy = cell.getY() - origin.getY();
		int dx = cell.getX() - origin.getX();
		if (dx == 0 && dy == 0)
			return new BigDecimal(0);
		if (dx == 0) {
			if (dy > 0)
				return new BigDecimal(270);
			else
				return new BigDecimal(90);
		}
		if (dy == 0) {
			if (dx > 0)
				return new BigDecimal(0);
			else
				return new BigDecimal(180);
		}
		// FIRST QUADRANT
		if (dx > 0 && dy < 0) {
			return GeneralOperations.tanInverse(GeneralOperations.divideBD(dy, dx)).multiply(new BigDecimal(-1));
		}// SECOND QUADRANT
		if (dx < 0 && dy < 0) {
			return new BigDecimal(180).subtract(GeneralOperations.tanInverse(GeneralOperations.divideBD(dy, dx)));
		}// THIRD QUADRANT
		if (dx < 0 && dy > 0) {
			return new BigDecimal(180).subtract(GeneralOperations.tanInverse(GeneralOperations.divideBD(dy, dx)));
		}// FOURTH QUADRANT
		if (dx > 0 && dy > 0) {
			return new BigDecimal(360).subtract(GeneralOperations.tanInverse(GeneralOperations.divideBD(dy, dx)));
		}

		return GeneralOperations.tanInverse(GeneralOperations.divideBD(dy, dx));
	}

	public static Cell plusY(Cell cell, int i) {
		return new Cell(cell.getX(), cell.getY() + i);
	}

	public static Cell plusX(Cell cell, int i) {
		return new Cell(cell.getX() + i, cell.getY());
	}

	public static Cell minusY(Cell cell, int i) {
		return new Cell(cell.getX(), cell.getY() - i);
	}

	public static Cell minusX(Cell cell, int i) {
		return new Cell(cell.getX() - i, cell.getY());
	}

	public static Cell getRandomCell(int size) {
		Random generator = new Random();
		return new Cell(generator.nextInt(size), generator.nextInt(size));
	}

	public static int getDistance(Cell first, Cell second) {
		if (first == null || second == null)
			return -1;
		return Math.abs(first.getX() - second.getX()) + Math.abs(first.getY() - second.getY());
	}

	public static Boolean isPathValid(List<Cell> path) {
		Cell frst, scnd;
		Boolean isPathValid = true;
		for (int i = 0; i < path.size() - 1; i++) {
			frst = path.get(i);
			scnd = path.get(i + 1);
			if (getDistance(frst, scnd) != 1) {
				isPathValid = false;
				break;
			}
		}
		return isPathValid;
	}

	public static Boolean isCellValid(Cell cell, int gridSize) {
		if (cell.getX() >= 0 && cell.getX() < gridSize && cell.getY() >= 0 && cell.getY() < gridSize) {
			return true;
		}
		return false;
	}

	public static List<MoveCode> getUpdatedMCodeList(MoveCode start, List<MoveCode> mcode) {
		List<MoveCode> updMCode = new ArrayList<MoveCode>();
		updMCode.add(start);
		for (MoveCode m : mcode) {
			updMCode.add(m);
		}
		return updMCode;
	}

	public static List<HierDividedArea> getHierDividedAreasForHilbert(int gridSize, int numAgents) {
		List<HierDividedArea> lst = new ArrayList<HierDividedArea>();
		int size = (int) (gridSize / Math.sqrt(numAgents));
		HilOrient i1, i2, i3, i4;
		if (new Random().nextBoolean()) {
			i1 = HilOrient.RIGHT_DOWN;
			i2 = HilOrient.LEFT_DOWN;
			i3 = HilOrient.LEFT_UP;
			i4 = HilOrient.RIGHT_UP;
		} else {
			i1 = HilOrient.DOWN_RIGHT;
			i2 = HilOrient.DOWN_LEFT;
			i3 = HilOrient.UP_LEFT;
			i4 = HilOrient.UP_RIGHT;
		}
		int k = 0;
		for (int col = size - 1; col < gridSize; col = col + 2 * size) {
			for (int row = size - 1; row < gridSize; row = row + 2 * size) {
				Cell c = new Cell(row, col);
				lst.add(new HierDividedArea(c, StartPos.BOTTOM_RIGHT, size, i1));
				k++;
				if (k >= numAgents)
					break;
				c = Operations.plusX(c, 1);
				lst.add(new HierDividedArea(c, StartPos.BOTTOM_LEFT, size, i2));
				k++;
				if (k >= numAgents)
					break;
				c = Operations.plusY(c, 1);
				lst.add(new HierDividedArea(c, StartPos.TOP_LEFT, size, i3));
				k++;
				if (k >= numAgents)
					break;
				c = Operations.minusX(c, 1);
				lst.add(new HierDividedArea(c, StartPos.TOP_RIGHT, size, i4));
				k++;
				if (k >= numAgents)
					break;
			}
		}
		return lst;
	}

	@SuppressWarnings("unchecked")
	public static List<Cell> sortCellsOnX(List<Cell> cells, Boolean asc) {
		Map<Cell, Integer> map = new HashMap<Cell, Integer>();
		for (Cell c : cells) {
			map.put(c, c.getX());
		}
		map = GeneralOperations.sortMapByValue(map);
		List<Cell> sorted = new ArrayList<Cell>(map.keySet());
		if (!asc)
			Collections.reverse(sorted);
		return sorted;
	}

	@SuppressWarnings("unchecked")
	public static List<Cell> sortCellsOnY(List<Cell> cells, Boolean asc) {
		Map<Cell, Integer> map = new HashMap<Cell, Integer>();
		for (Cell c : cells) {
			map.put(c, c.getY());
		}
		map = GeneralOperations.sortMapByValue(map);
		List<Cell> sorted = new ArrayList<Cell>(map.keySet());
		if (!asc)
			Collections.reverse(sorted);
		return sorted;
	}

	public static List<HierDividedArea> getHierDividedAreasForZigZag(int gridSize, int numAgents) {
		List<HierDividedArea> lst = new ArrayList<HierDividedArea>();
		int size = (int) (gridSize / Math.sqrt(numAgents));
		ZigZagOrient i1, i2, i3, i4;
		// if (new Random().nextBoolean()) {
		// i1 = ZigZagOrient.RIGHT_DOWN;
		// i2 = ZigZagOrient.LEFT_DOWN;
		// i3 = ZigZagOrient.LEFT_UP;
		// i4 = ZigZagOrient.RIGHT_UP;
		// } else {
		// i1 = ZigZagOrient.DOWN_RIGHT;
		// i2 = ZigZagOrient.DOWN_LEFT;
		// i3 = ZigZagOrient.UP_LEFT;
		// i4 = ZigZagOrient.UP_RIGHT;
		// }

		i1 = ZigZagOrient.DOWN_RIGHT;
		i2 = ZigZagOrient.DOWN_LEFT;
		i3 = ZigZagOrient.UP_LEFT;
		i4 = ZigZagOrient.UP_RIGHT;
		int k = 0;
		for (int col = size - 1; col < gridSize; col = col + 2 * size) {
			for (int row = size - 1; row < gridSize; row = row + 2 * size) {
				Cell c = new Cell(row, col);
				lst.add(new HierDividedArea(c, StartPos.BOTTOM_RIGHT, size, i1));
				k++;
				if (k >= numAgents)
					break;
				c = Operations.plusX(c, 1);
				lst.add(new HierDividedArea(c, StartPos.BOTTOM_LEFT, size, i2));
				k++;
				if (k >= numAgents)
					break;
				c = Operations.plusY(c, 1);
				lst.add(new HierDividedArea(c, StartPos.TOP_LEFT, size, i3));
				k++;
				if (k >= numAgents)
					break;
				c = Operations.minusX(c, 1);
				lst.add(new HierDividedArea(c, StartPos.TOP_RIGHT, size, i4));
				k++;
				if (k >= numAgents)
					break;
			}
		}
		return lst;
	}

	public static List<Cell> getGrid(int gridSize) {
		List<Cell> grid = new ArrayList<Cell>();
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				grid.add(new Cell(j, i));
			}
		}
		return grid;
	}

	@SuppressWarnings("unchecked")
	public static List<Path> setTimesAndDepPathsAndPathsFromOriginForHierDivision(List<Path> lstPaths, int numAgents, int gridSize) {
		if (numAgents == 4) {
			return getHier4AgentPaths(lstPaths, gridSize);
		}
		lstPaths = setQuadrants(lstPaths, gridSize);
		List<Path> updated = new ArrayList<Path>();
		Cell decision1 = new Cell(gridSize - 1, gridSize / 2 - 1);
		Cell decision2 = new Cell(0, gridSize / 2 - 1);
		Cell decision3 = new Cell(0, gridSize / 2);
		Cell decision4 = new Cell(gridSize - 1, gridSize / 2);
		Map<Path, Integer> map1 = new HashMap<Path, Integer>();
		Map<Path, Integer> map2 = new HashMap<Path, Integer>();
		Map<Path, Integer> map3 = new HashMap<Path, Integer>();
		Map<Path, Integer> map4 = new HashMap<Path, Integer>();
		for (Path temp : lstPaths) {
			if (temp.getQuad() == 1)
				map1.put(temp, Operations.getDistance(temp.getStart(), decision1));
			else if (temp.getQuad() == 2)
				map2.put(temp, Operations.getDistance(temp.getStart(), decision2));
			else if (temp.getQuad() == 3)
				map3.put(temp, Operations.getDistance(temp.getStart(), decision3));
			else if (temp.getQuad() == 4)
				map4.put(temp, Operations.getDistance(temp.getStart(), decision4));
		}

		map1 = GeneralOperations.sortMapByValue(map1);
		map2 = GeneralOperations.sortMapByValue(map2);
		map3 = GeneralOperations.sortMapByValue(map3);
		map4 = GeneralOperations.sortMapByValue(map4);

		List<Path> lst1 = new ArrayList<Path>(map1.keySet());
		List<Path> lst2 = new ArrayList<Path>(map2.keySet());
		List<Path> lst3 = new ArrayList<Path>(map3.keySet());
		List<Path> lst4 = new ArrayList<Path>(map4.keySet());

		Cell origin2 = new Cell(gridSize / 2 - 1, gridSize / 2 - 1);
		Cell origin1 = Operations.plusX(origin2, 1);
		Cell origin4 = Operations.plusY(origin1, 1);
		Cell origin3 = Operations.minusX(origin4, 1);

		int k = numAgents / 4;
		for (int i = 0; i < numAgents / 4; i++) {
			lst1.get(i).setStartTime(k);
			lst1.get(i).setDepPath(getExtendedDepPath(origin1, decision1, lst1.get(i)));
			lst2.get(i).setStartTime(k);
			lst2.get(i).setDepPath(getExtendedDepPath(origin2, decision2, lst2.get(i)));
			lst3.get(i).setStartTime(k);
			lst3.get(i).setDepPath(getExtendedDepPath(origin3, decision3, lst3.get(i)));
			lst4.get(i).setStartTime(k);
			lst4.get(i).setDepPath(getExtendedDepPath(origin4, decision4, lst4.get(i)));
			k--;
		}

		updated.addAll(lst1);
		updated.addAll(lst2);
		updated.addAll(lst3);
		updated.addAll(lst4);
		updated = setTimeSteps(updated);
		updated = setCompletePaths(updated);

		return updated;
	}

	private static List<Path> setQuadrants(List<Path> lstPaths, int g) {
		Cell start;
		for (Path p : lstPaths) {
			start = p.getStart();
			if (start.getX() > g / 2 - 1 && start.getY() <= g / 2 - 1)
				p.setQuad(1);
			else if (start.getX() <= g / 2 - 1 && start.getY() <= g / 2 - 1)
				p.setQuad(2);
			else if (start.getX() <= g / 2 - 1 && start.getY() > g / 2 - 1)
				p.setQuad(3);
			else if (start.getX() > g / 2 - 1 && start.getY() > g / 2 - 1)
				p.setQuad(4);
		}
		return lstPaths;
	}

	public static Path getPathWithID(int n, List<Path> paths) {
		for (Path p : paths) {
			if (p.getId() == n)
				return p;
		}
		return null;
	}

	private static List<Path> getHier4AgentPaths(List<Path> lstPaths, int gridSize) {
		for (Path p : lstPaths) {
			p.setStartTime(0);
			p.setDepPath(new ArrayList<Cell>(Arrays.asList(p.getPath().remove(0))));
			p.setCompletePath(p.getPath());
		}
		return lstPaths;
	}

	private static List<Cell> getExtendedDepPath(Cell origin, Cell decision, Path path) {
		List<Cell> cells = new ArrayList<Cell>();
		for (int i = 0; i < path.getStartTime() - 1; i++) {
			cells.add(origin);
		}
		cells.addAll(PathOperations.getDeltaXPath(origin, decision));
		cells.addAll(PathOperations.getDeltaYPath(decision, path.getStart()));
		return cells;
	}

	private static List<Path> setTimeSteps(List<Path> updated) {
		List<Integer> lstDeps = new ArrayList<Integer>();
		for (Path p : updated) {
			p.setDepTime(p.getStartTime() + p.getDepPath().size() - 2);
			p.setExpTime(p.getStartTime() + p.getPath().size() - 2);
			lstDeps.add(p.getDepTime());
		}
		int startExpTime = Collections.max(lstDeps);
		for (Path p : updated) {
			p.setPath(assignTimeSteps(p.getPath(), startExpTime));
		}
		return updated;
	}

	public static List<Cell> assignTimeSteps(List<Cell> path, int startExploringTime) {
		int k = startExploringTime;
		for (Cell c : path) {
			c.setTimeStep(k++);
		}
		return path;
	}

	public static List<Cell> getNeighbours(Cell cell, int hops, int gridSize) {

		List<Cell> neighbours = new ArrayList<Cell>();
		for (int i = 0; i < hops; i++) {
			neighbours.addAll(addLeftAndRightCells(Operations.plusY(cell, hops - i), i));
			neighbours.addAll(addLeftAndRightCells(Operations.minusY(cell, hops - i), i));
		}

		neighbours.addAll(addLeftAndRightCells(cell, hops));

		// remove the cell itself from it's neighbors list
		neighbours.removeAll(Collections.singleton(cell));

		// remove any duplicate cells from the list
		neighbours = new ArrayList<Cell>(new HashSet<Cell>(neighbours));

		List<Cell> validList = new ArrayList<Cell>();
		for (Cell c : neighbours) {
			if (isCellValid(c, gridSize))
				validList.add(c);
		}
		return validList;
	}

	private static List<Cell> addLeftAndRightCells(Cell c, int hops) {
		List<Cell> temp = new ArrayList<Cell>();
		if (hops > 0) {
			for (int i = 1; i <= hops; i++) {
				temp.add(Operations.plusX(c, i));
				temp.add(Operations.minusX(c, i));
			}
		}
		temp.add(c);
		return temp;
	}

	private static List<Path> setCompletePaths(List<Path> updated) {
		List<Cell> compPath;
		for (Path p : updated) {
			compPath = new ArrayList<Cell>();
			compPath.addAll(p.getDepPath());
			compPath.addAll(p.getPath());
			p.setCompletePath(compPath);
		}
		return updated;
	}

	public static void main(String[] args) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		System.out.println(map.put(1, "g"));
	}

}
