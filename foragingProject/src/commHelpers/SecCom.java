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

public class SecCom {

	private int gridSize;
	private int numAgents;
	private Agent agent;
	private int clock;
	private List<Path> paths;

	public SecCom(int gridSize, int numAgents, Agent agent, Cell foodcell, int clock, List<Path> paths) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		this.agent = agent;
		this.clock = clock;
		this.paths = paths;
	}

	public Agent getAgentWithCommPathSet() {
		if (agent.getFoodLocator())
			getFoodLocatorCommPathSet();
		else
			getNonFoodLocatorCommPath();
		return agent;
	}

	private void getFoodLocatorCommPathSet() {
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
		agent.setCommPath(best.getCommPath());
	}

	private Path getPathWithID(int n) {
		for (Path p : paths) {
			if (p.getId() == n)
				return p;
		}
		return null;
	}

	private void getNonFoodLocatorCommPath() {
		WaitingCell bestCell = new PointOfContact(agent.getMove(), getPathWithID(agent.getTransmitTo()),clock).get();
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
		agent.setCommPath(pa);
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

}
