package commHelpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.GeneralOperations;
import operations.Operations;
import operations.PathOperations;
import patterns.AdvancedSectors;
import entities.Agent;
import entities.Cell;
import entities.Path;
import entities.WaitingCell;

public class AdvancedSectorComm {

	private int gridSize;
	private Agent agent;
	private int clock;
	private List<Path> paths;

	private List<Cell> commPath;

	public AdvancedSectorComm(int gridSize, int numAgents, Agent agent, Cell food, int clock, List<Path> paths) {
		this.gridSize = gridSize;
		this.agent = agent;
		this.clock = clock;
		this.paths =new AdvancedSectors().getPaths();
		process();
	}

	private void process() {
		WaitingCell bestCell = new PointOfContact(agent.getMove(), getPathWithID(agent.getTransmitTo()), clock).get();
		Cell bestNeigh = getNeighOfBestCell(agent.getMove(), bestCell.getCell());
		List<Cell> pa = PathOperations.getValidRandomPath(agent.getMove(), bestNeigh, gridSize);
		pa.remove(0);
		if (bestCell.getWait() > 0) {
			for (int i = 0; i < bestCell.getWait(); i++) {
				pa.add(bestNeigh);
			}
		} else {
			pa.add(bestNeigh);
		}
		commPath = pa;
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
