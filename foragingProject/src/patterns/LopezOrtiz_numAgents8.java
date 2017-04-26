package patterns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import operations.Operations;
import operations.PathOperations;
import operations.VerifyPattern;

import comparators.PathComparator;

import entities.Cell;
import entities.ConfigLopOrt8_even;
import entities.ConfigLopOrt8_odd;
import entities.Path;
import enums.MoveCode;

public class LopezOrtiz_numAgents8 {

	private List<Path> paths;
	private int gridSize;
	private Cell origin;
	private List<Cell> cellsToCover;

	public LopezOrtiz_numAgents8(int gridSize) {
		this.paths = new ArrayList<Path>();
		this.gridSize = gridSize;
		this.origin = Operations.getOrigin(gridSize);
		initializeCellsToCover();
		populatePaths();
		truncatePaths();
	}

	private void populatePaths() {
		paths.add(new Path(0, getPath(new ConfigLopOrt8_odd(1).getMCodes(), origin), 0));
		paths.add(new Path(2, getPath(new ConfigLopOrt8_odd(3).getMCodes(), origin), 0));
		paths.add(new Path(4, getPath(new ConfigLopOrt8_odd(5).getMCodes(), origin), 0));
		paths.add(new Path(6, getPath(new ConfigLopOrt8_odd(7).getMCodes(), origin), 0));

		paths.add(new Path(1, getPath(new ConfigLopOrt8_even(2).getMCodes(), origin), 1));
		paths.add(new Path(3, getPath(new ConfigLopOrt8_even(4).getMCodes(), origin), 1));
		paths.add(new Path(5, getPath(new ConfigLopOrt8_even(6).getMCodes(), origin), 1));
		paths.add(new Path(7, getPath(new ConfigLopOrt8_even(8).getMCodes(), origin), 1));
	}

	private void initializeCellsToCover() {
		cellsToCover = new ArrayList<Cell>();
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				cellsToCover.add(new Cell(i, j));
			}
		}
		cellsToCover.add(origin);
		cellsToCover.add(origin);
		cellsToCover.add(origin);
		cellsToCover.add(origin);
		cellsToCover.add(origin);
		cellsToCover.add(origin);
		cellsToCover.add(origin);
	}

	private void truncatePaths() {
		List<Cell> cells;
		for (Path p : paths) {
			cells = truncateCells(p.getPath());
			cells = validatePaths(cells);
			p.setPath(cells);
		}
	}

	private void setTimeSteps() {
		List<Cell> cells;
		Path temp;
		for (int i = 0; i < paths.size(); i++) {
			temp = paths.get(i);
			cells = setTimeSteps(temp.getPath(), temp.getStartTime());
			temp.setPath(cells);
			paths.set(i, temp);
		}
	}

	private List<Cell> setTimeSteps(List<Cell> cells, int start) {
		Cell c;
		for (int i = 0; i < cells.size(); i++) {
			c = cells.get(i);
			c.setTimeStep(i);
			cells.set(i, c);
		}
		return cells;
	}

	private List<Cell> validatePaths(List<Cell> cells) {
		List<Cell> ucells = new ArrayList<Cell>();
		Cell src = new Cell();
		Cell dest = new Cell();
		for (int i = 0; i < cells.size() - 1; i++) {
			src = cells.get(i);
			dest = cells.get(i + 1);
			if (Operations.getDistance(src, dest) == 1)
				ucells.add(src);
			else {
				ucells.addAll(PathOperations.getValidPath(src, dest, gridSize));
			}
		}
		ucells.add(dest);
		return ucells;
	}

	private List<Cell> truncateCells(List<Cell> path) {
		List<Cell> ucells = new ArrayList<Cell>();
		for (Cell c : path) {
			if (Operations.isCellValid(c, gridSize) && cellsToCover.contains(c)) {
				ucells.add(c);
				cellsToCover.remove(c);
			}
		}
		return ucells;
	}

	private List<Cell> getPath(List<MoveCode> starts, Cell start) {
		List<Cell> lstCells = new ArrayList<Cell>();
		lstCells.add(start);
		for (int i = 0; i < starts.size(); i++) {
			switch (starts.get(i)) {
			case START:
				break;
			case PLUS_X:
				start = Operations.plusX(start, 1);
				lstCells.add(start);
				break;
			case MINUS_X:
				start = Operations.minusX(start, 1);
				lstCells.add(start);
				break;
			case PLUS_Y:
				start = Operations.plusY(start, 1);
				lstCells.add(start);
				break;
			case MINUS_Y:
				start = Operations.minusY(start, 1);
				lstCells.add(start);
				break;
			}
		}
		return lstCells;
	}

	public List<Path> getPaths() {
		Collections.sort(paths, new PathComparator());
		Path temp;
		List<Cell> cells;
		for (int i = 0; i < paths.size(); i++) {
			temp = paths.get(i);
			cells = new ArrayList<Cell>();
			if (temp.getStartTime() == 1) {
				cells.add(origin);
				cells.addAll(temp.getPath());
				temp.setPath(cells);
				paths.set(i, temp);
			}
		}
		setTimeSteps();
		return paths;
	}

	public LopezOrtiz_numAgents8() {
		super();
	}

	public static void main(String[] args) {
		VerifyPattern.drawIt(new LopezOrtiz_numAgents8(60).paths, 1000);
		//GeneralOperations.displayCollection(new LopezOrtiz_numAgents8(100).getPaths());
	}

}
