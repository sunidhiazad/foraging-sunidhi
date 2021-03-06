package commHelpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.GeneralOperations;
import operations.Operations;
import operations.PathOperations;
import entities.Agent;
import entities.Cell;
import entities.Path;
import entities.SubConfiguration;
import entities.WaitingCell;

public class LopezComm8 {

	private int gridSize;
	private Agent agent;
	private int clock;
	private List<Path> paths;

	private List<Cell> commPath;
	private int numAgents;

	public LopezComm8(int gridSize, Agent ag, int clock, List<Path> paths) {
		this.gridSize = gridSize;
		this.agent = ag;
		this.clock = clock;
		this.paths = paths;
		this.numAgents = 8;
		if (ag.getFoodLocator())
			processForFoodLocator();
		else
			processForNonFoodLocator();

	}

	private void processForFoodLocator() {
		int n1 = -1;
		int n2 = -1;

		if (agent.getId() == 0) {
			n1 = numAgents - 1;
			n2 = 1;
		} else if (agent.getId() == numAgents - 1) {
			n1 = agent.getId() - 1;
			n2 = 0;
		} else {
			n1 = agent.getId() - 1;
			n2 = agent.getId() + 1;
		}

		SubConfiguration best = new CommConfig(getPathWithID(n1), getPathWithID(n2), agent.getMove(), gridSize, clock).getConfiguration();
		this.commPath = best.getCommPath();
	}

	private void processForNonFoodLocator() {
		WaitingCell bestCell = new PointOfContact(agent.getMove(), getPathWithID(agent.getTransmitTo()), clock).get();
		Cell bestNeigh = getNeighOfBestCell(agent.getMove(), bestCell.getCell());
		List<Cell> pa = PathOperations.getValidRandomPath(agent.getMove(), bestNeigh, gridSize);
		pa.add(bestNeigh);
		pa.remove(0);
		if (bestCell.getWait() > 0) {
			for (int i = 0; i < bestCell.getWait(); i++) {
				pa.add(bestNeigh);
			}
		}
		this.commPath = pa;
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
