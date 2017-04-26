package commHelpers;

import java.util.ArrayList;
import java.util.List;

import operations.Operations;
import operations.PathOperations;
import entities.Agent;
import entities.Cell;

public class StripeCom {

	private int gridSize;
	private int stripSize;
	private Agent agent;
	private List<Cell> commPath;

	public StripeCom(int gridSize, int numAgents, Agent ag) {
		this.gridSize = gridSize;

		this.stripSize = gridSize / numAgents;
		this.agent = ag;
		this.commPath = new ArrayList<Cell>();
		setCommunicationPath();
	}

	private void setCommunicationPath() {
		Cell curr = agent.getMove();
		Cell dest = Cell.NULL_CELL;
		Cell start = new Cell(gridSize - 1, agent.getPath().getStart().getY());
		Cell end;
		boolean oddStripe = (agent.getId() % 2 == 1) ? true : false;
		if (oddStripe) {
			end = Operations.plusY(start, stripSize - 1);
		} else {
			end = Operations.minusY(start, stripSize - 1);
		}
		if (curr.equals(start))
			dest = end;
		else
			dest = start;
		commPath.addAll(PathOperations.getDeltaYPath(curr, dest));
		commPath.add(dest);
		commPath = commPath.subList(1, commPath.size());
	}

	public Agent getAgentWithCommPathSet() {
		agent.setCommPath(commPath);
		return agent;
	}

}
