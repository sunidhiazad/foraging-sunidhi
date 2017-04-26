package commHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.GeneralOperations;
import operations.Operations;
import operations.PathOperations;
import patterns.Hilbert;
import patterns.ZigZag;
import entities.Agent;
import entities.Cell;
import entities.Path;
import enums.Pattern;

public class HierComm {

	private int gridSize;
	private List<Path> paths;
	private Agent agent;
	private List<Cell> commPath;

	public HierComm(int gridSize, int numAgents, Agent agent, Cell foodcell, Pattern pattern) {
		this.gridSize = gridSize;
		if (pattern.equals(Pattern.HILBERT))
			this.paths = new Hilbert(gridSize, numAgents).getPaths();
		else if (pattern.equals(Pattern.ZIG_ZAG))
			this.paths = new ZigZag(gridSize, numAgents).getPaths();
		this.agent = new HierTransmission(agent, paths).getAgentWithTransmissionSet();
		this.commPath = new ArrayList<Cell>();

		setAgentsCommPath();
	}

	private void setAgentsCommPath() {
		Cell current = agent.getMove();
		Map<Cell, Integer> map = new HashMap<Cell, Integer>();
		Path temp;
		for (Integer i : agent.getHierTransmitTo()) {
			temp = getPathWithID(i);
			map.put(temp.getLast(), Operations.getDistance(current, temp.getLast()));
		}
		GeneralOperations.sortMapByValueAsc(map);
		map.values().removeAll(Collections.singleton(1));
		List<Cell> dests = new ArrayList<Cell>(map.keySet());
		Cell best = Cell.NULL_CELL;
		for (Cell c : dests) {
			best = getNeighOfBestCell(current, c);
			commPath.addAll(PathOperations.getValidPath(current, best, gridSize));
			current = c;
		}
		commPath.add(best);
	}

	@SuppressWarnings({ "unchecked" })
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

	private Path getPathWithID(int n) {
		for (Path p : paths) {
			if (p.getId() == n)
				return p;
		}
		return null;
	}

	public Agent getAgentWithCommPathSet() {
		agent.setCommPath(commPath);
		return agent;
	}

}
