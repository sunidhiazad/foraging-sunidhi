package commHelpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.GeneralOperations;
import operations.Operations;
import entities.Cell;
import entities.Path;
import entities.SubConfiguration;
import entities.WaitingCell;

public class CommConfig {
	private List<Cell> neighbour1;
	private List<Cell> neighbour2;
	private Cell locator;
	private int gridSize;

	private Path path1;
	private Path path2;
	private int clock;

	public CommConfig(Path path1, Path path2, Cell locator, int gridSize, int clock) {
		super();
		this.path1 = path1;
		this.path2 = path2;
		this.locator = locator;
		this.gridSize = gridSize;
		this.clock = clock;
	}

	public SubConfiguration getConfiguration() {
		WaitingCell wcell12_1 = new PointOfContact(locator, path1,clock).get();
		Cell frst12 = wcell12_1.getCell();
		Cell neigh_frst12 = getNeighOfBestCell(locator, frst12);
		neigh_frst12.setTimeStep(frst12.getTimeStep());

		WaitingCell wcell12_2 = new PointOfContact(neigh_frst12, path2,clock).get();
		Cell scnd12 = wcell12_2.getCell();
		Cell neigh_scnd12 = getNeighOfBestCell(neigh_frst12, scnd12);
		neigh_scnd12.setTimeStep(scnd12.getTimeStep());

		SubConfiguration sc12 = new SubConfiguration(frst12, scnd12, neigh_frst12, neigh_scnd12, locator, gridSize);
		sc12.setWaitingCells(wcell12_1, wcell12_2);

		WaitingCell wcell21_1 = new PointOfContact(locator, path2,clock).get();
		Cell frst21 = wcell21_1.getCell();
		Cell neigh_frst21 = getNeighOfBestCell(locator, frst21);
		neigh_frst21.setTimeStep(frst21.getTimeStep());

		WaitingCell wcell21_2 = new PointOfContact(neigh_frst21, path1,clock).get();
		Cell scnd21 = wcell21_2.getCell();
		Cell neigh_scnd21 = getNeighOfBestCell(neigh_frst21, scnd21);
		neigh_scnd21.setTimeStep(scnd21.getTimeStep());

		SubConfiguration sc21 = new SubConfiguration(frst21, scnd21, neigh_frst21, neigh_scnd21, locator, gridSize);
		sc21.setWaitingCells(wcell21_1, wcell21_2);
		return bestOf(sc12, sc21);
	}

	@SuppressWarnings("unchecked")
	private Cell getNeighOfBestCell(Cell src, Cell bestCell) {
		if (bestCell.equals(Cell.NULL_CELL))
			return Cell.NULL_CELL;
		Cell north = Operations.minusY(bestCell, 1);
		Cell south = Operations.plusY(bestCell, 1);
		Cell west = Operations.minusX(bestCell, 1);
		Cell east = Operations.plusX(bestCell, 1);
		Map<Cell, Integer> map = new HashMap<Cell, Integer>();
		if (Operations.isCellValid(north, gridSize))
			map.put(north, Operations.getDistance(north, src));
		if (Operations.isCellValid(south, gridSize))
			map.put(south, Operations.getDistance(south, src));
		if (Operations.isCellValid(west, gridSize))
			map.put(west, Operations.getDistance(west, src));
		if (Operations.isCellValid(east, gridSize))
			map.put(east, Operations.getDistance(east, src));
		map = GeneralOperations.sortMapByValue(map);
		Cell neiCell = (Cell) map.keySet().toArray()[0];
		neiCell.setPathid(bestCell.getPathid());
		return neiCell;
	}

	private SubConfiguration bestOf(SubConfiguration sc12, SubConfiguration sc21) {
		if (sc12.getMaxTS() < sc21.getMaxTS())
			return sc12;
		else
			return sc21;
	}

	public List<Cell> getNeighbour1() {
		return neighbour1;
	}

	public void setNeighbour1(List<Cell> neighbour1) {
		this.neighbour1 = neighbour1;
	}

	public List<Cell> getNeighbour2() {
		return neighbour2;
	}

	public void setNeighbour2(List<Cell> neighbour2) {
		this.neighbour2 = neighbour2;
	}

	public Cell getLocator() {
		return locator;
	}

	public void setLocator(Cell locator) {
		this.locator = locator;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}
}
