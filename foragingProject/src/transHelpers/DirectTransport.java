package transHelpers;

import java.util.ArrayList;
import java.util.List;

import operations.Operations;
import entities.Agent;
import entities.Cell;

public class DirectTransport {

	private Agent agent;
	private List<Cell> snapshot;
	private int gridSize;
	private Cell origin;
	private Cell target;
	private Cell current;

	private Cell nextMove;

	public DirectTransport(Agent agent, List<Cell> snapshot, Cell food, int gridSize) {
		this.agent = agent;
		this.snapshot = snapshot;
		this.gridSize = gridSize;
		this.origin = Operations.getOrigin(gridSize);
		if (agent.isFoodpossessed())
			this.target = origin;
		else
			this.target = food;
		this.current = agent.getMove();
		decideNextMove();
	}

	private void decideNextMove() {
		List<Cell> options = getFourOptions(current, target);
		List<Cell> possible = getPossibleMoves(options);
		if (possible.size() == 0)
			nextMove = current; // STAY PUT
		else
			nextMove = possible.get(0);
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

	private List<Cell> getFourOptions(Cell current, Cell target) {
		List<Cell> options = new ArrayList<Cell>();
		Cell north = Operations.minusY(current, 1);
		Cell south = Operations.plusY(current, 1);
		Cell west = Operations.minusX(current, 1);
		Cell east = Operations.plusX(current, 1);
		if (Operations.isCellValid(north, gridSize) && Operations.getDistance(current, target) > Operations.getDistance(north, target))
			options.add(north);
		if (Operations.isCellValid(south, gridSize) && Operations.getDistance(current, target) > Operations.getDistance(south, target))
			options.add(south);
		if (Operations.isCellValid(west, gridSize) && Operations.getDistance(current, target) > Operations.getDistance(west, target))
			options.add(west);
		if (Operations.isCellValid(east, gridSize) && Operations.getDistance(current, target) > Operations.getDistance(east, target))
			options.add(east);
		return options;
	}

	public Agent getNextMove() {
		agent.setMove(nextMove);
		return agent;
	}
}
