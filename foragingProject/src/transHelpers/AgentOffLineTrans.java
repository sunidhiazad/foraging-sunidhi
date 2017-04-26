package transHelpers;

import java.util.ArrayList;
import java.util.List;

import operations.Operations;
import entities.Agent;
import entities.Cell;

public class AgentOffLineTrans {

	private Agent agent;
	private List<Cell> foodToNest;
	private List<Cell> snapshot;
	private int gridSize;
	private Cell move;

	public AgentOffLineTrans(Agent agent, List<Cell> foodToNest, List<Cell> snapshot, int gridSize) {
		this.agent = agent;
		this.foodToNest = foodToNest;
		this.snapshot = snapshot;
		this.gridSize = gridSize;
		//processForClosestCellOnThePath();
		processForFood();
	}

	public void processForClosestCellOnThePath() {
		Cell closest = getClosestCellOnThePath();
		List<Cell> options = getPossibleMoves(agent.getMove(), closest);
		List<Cell> possible = getPossibleMoves(options);
		if (possible.size() == 0)
			move = agent.getMove();
		else
			move = possible.get(0);
	}
	
	public void processForFood() {
		List<Cell> options = getPossibleMoves(agent.getMove(), foodToNest.get(0));
		List<Cell> possible = getPossibleMoves(options);
		if (possible.size() == 0)
			move = agent.getMove();
		else
			move = possible.get(0);
	}

	private List<Cell> getPossibleMoves(List<Cell> options) {
		List<Cell> poss = new ArrayList<Cell>();
		List<Cell> redzone = new ArrayList<Cell>();
		List<Cell> threats = new ArrayList<Cell>();
		int x = agent.getMove().getX();
		int y = agent.getMove().getY();
		for (Cell c : options) {
			if (y - c.getY() == -1) { // MOVING SOUTH
				redzone.add(new Cell(x, y + 2));
				redzone.add(new Cell(x + 1, y + 1));
				redzone.add(new Cell(x - 1, y + 1));
			} else if (y - c.getY() == 1) { // MOVING NORTH
				redzone.add(new Cell(x, y - 2));
				redzone.add(new Cell(x - 1, y - 1));
				redzone.add(new Cell(x + 1, y - 1));
			} else if (x - c.getX() == -1) { // MOVING EAST
				redzone.add(new Cell(x + 2, y));
				redzone.add(new Cell(x + 1, y - 1));
				redzone.add(new Cell(x + 1, y + 1));
			} else if (x - c.getX() == 1) { // MOVING WEST
				redzone.add(new Cell(x - 2, y));
				redzone.add(new Cell(x - 1, y + 1));
				redzone.add(new Cell(x - 1, y - 1));
			}
			for (Cell d : redzone) {
				if (snapshot.contains(d))
					threats.add(d);
			}
			Boolean pass = true;
			if (threats.size() > 0) {
				for (Cell e : threats) {
					if (e.getX() > c.getX())
						pass = false;
					else if (e.getX() == c.getX()) {
						if (e.getY() > c.getY())
							pass = false;
					}
				}
			}
			if (pass)
				poss.add(c);
		}
		return poss;
	}

	private List<Cell> getPossibleMoves(Cell move, Cell closest) {
		List<Cell> options = new ArrayList<Cell>();
		Cell north = Operations.minusY(move, 1);
		Cell south = Operations.plusY(move, 1);
		Cell west = Operations.minusX(move, 1);
		Cell east = Operations.plusX(move, 1);
		if (Operations.isCellValid(north, gridSize) && Operations.getDistance(move, closest) > Operations.getDistance(north, closest))
			options.add(north);
		if (Operations.isCellValid(south, gridSize) && Operations.getDistance(move, closest) > Operations.getDistance(south, closest))
			options.add(south);
		if (Operations.isCellValid(west, gridSize) && Operations.getDistance(move, closest) > Operations.getDistance(west, closest))
			options.add(west);
		if (Operations.isCellValid(east, gridSize) && Operations.getDistance(move, closest) > Operations.getDistance(east, closest))
			options.add(east);
		return options;
	}

	private Cell getClosestCellOnThePath() {
		Cell current = agent.getMove();
		Cell point = foodToNest.get(0);
		for (Cell c : foodToNest) {
			if (Operations.getDistance(current, c) < Operations.getDistance(current, point))
				point = c;
		}
		return point;
	}

	public Agent getAgentWithNextMoveSet() {
		agent.setMove(move);
		return agent;
	}
}
