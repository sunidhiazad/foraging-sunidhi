package transHelpers;

import java.util.List;

import operations.Operations;
import entities.Cell;

public class AgentOnLineTrans {

	private Cell adjacent;
	private Cell head;
	private Cell left;
	private Cell right;
	private Cell current;
	private Cell food;

	private List<Cell> snapshot;
	private List<Cell> foodToNest;

	private Boolean agentAtAdjacent = false;
	private Boolean agentAtHead = false;
	private Boolean agentAtLeft = false;
	private Boolean agentAtRight = false;
	private Boolean agentAtLeftOnline = false;
	private Boolean agentAtRightOnline = false;

	private boolean importantThanHead = false;
	private boolean importantThanLeft = false;
	private boolean importantThanRight = false;

	public AgentOnLineTrans(Cell current, Cell intended, List<Cell> snapshot, List<Cell> foodToNest) {
		this.current = current;
		this.adjacent = intended;
		this.snapshot = snapshot;
		this.foodToNest = foodToNest;
		this.food = foodToNest.get(0);
		int x = current.getX();
		int y = current.getY();

		if (current.getY() - intended.getY() == -1) { // MOVING SOUTH
			this.head = new Cell(x, y + 2);
			this.left = new Cell(x + 1, y + 1);
			this.right = new Cell(x - 1, y + 1);
		} else if (current.getY() - intended.getY() == 1) { // MOVING NORTH
			this.head = new Cell(x, y - 2);
			this.left = new Cell(x - 1, y - 1);
			this.right = new Cell(x + 1, y - 1);
		} else if (current.getX() - intended.getX() == -1) { // MOVING EAST
			this.head = new Cell(x + 2, y);
			this.left = new Cell(x + 1, y - 1);
			this.right = new Cell(x + 1, y + 1);
		} else if (current.getX() - intended.getX() == 1) { // MOVING WEST
			this.head = new Cell(x - 2, y);
			this.left = new Cell(x - 1, y + 1);
			this.right = new Cell(x - 1, y - 1);
		}
		setFlags();
	}

	private void setFlags() {
		if (snapshot.contains(adjacent))
			this.agentAtAdjacent = true;
		if (snapshot.contains(head))
			this.agentAtHead = true;
		if (snapshot.contains(left))
			this.agentAtLeft = true;
		if (snapshot.contains(right))
			this.agentAtRight = true;
		if (foodToNest.contains(left)) {
			this.agentAtLeftOnline = true;
			if (Operations.getDistance(current, food) > Operations.getDistance(left, food))
				this.importantThanLeft = true;
		}
		if (foodToNest.contains(right)) {
			this.agentAtRightOnline = true;
			if (Operations.getDistance(current, food) > Operations.getDistance(right, food))
				this.importantThanRight = true;
		}
		if (agentAtHead) {
			if (!foodToNest.contains(head)) // head is OFFLINE
				this.importantThanHead = true;
			else if (head.getX() > current.getX())
				this.importantThanHead = true;
			else if (head.getX() < current.getX())
				this.importantThanHead = false;
			else {
				if (head.getY() > current.getY())
					this.importantThanHead = true;
				else if (head.getY() < current.getY())
					this.importantThanHead = false;
			}
		}
	}

	public Boolean getAgentAtAdjacent() {
		return agentAtAdjacent;
	}

	public Boolean getAgentAtHead() {
		return agentAtHead;
	}

	public Boolean getAgentAtLeft() {
		return agentAtLeft;
	}

	public Boolean getAgentAtRight() {
		return agentAtRight;
	}

	public boolean getImportantThanHead() {
		return importantThanHead;
	}

	public Boolean getAgentAtLeftOnline() {
		return agentAtLeftOnline;
	}

	public Boolean getAgentAtRightOnline() {
		return agentAtRightOnline;
	}

	public boolean isImportantThanLeft() {
		return importantThanLeft;
	}

	public boolean isImportantThanRight() {
		return importantThanRight;
	}

}
