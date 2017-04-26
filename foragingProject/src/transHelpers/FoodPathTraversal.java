package transHelpers;

import java.util.List;

import entities.Agent;
import entities.Cell;
import enums.TransDirection;

public class FoodPathTraversal {

	private Agent agent;
	private List<Cell> foodToNest;
	private List<Cell> snapshot;
	private Cell nextMove;
	private int gridSize;

	public FoodPathTraversal(Agent agent, List<Cell> foodToNest, List<Cell> snapshot, int gridSize) {
		super();
		this.agent = agent;
		this.foodToNest = foodToNest;
		this.snapshot = snapshot;
		this.gridSize = gridSize;
		if (agent.getMove().equals(foodToNest.get(0)))
			agent.setDirection(TransDirection.FOOD_TO_NEST);
		if (agent.getMove().equals(foodToNest.get(foodToNest.size() - 1)))
			agent.setDirection(TransDirection.NEST_TO_FOOD);
		if (foodToNest.contains(agent.getMove()))
			onLineDecide();
		else
			offLineDecide();
	}

	private void offLineDecide() {
		agent = new AgentOffLineTrans(agent, foodToNest, snapshot, gridSize).getAgentWithNextMoveSet();
		nextMove = agent.getMove();
	}

	private void onLineDecide() {
		int moveIndex = getMoveIndex();
		AgentOnLineTrans onLine = new AgentOnLineTrans(agent.getMove(), foodToNest.get(moveIndex), snapshot, foodToNest);
		nextMove = decideOnAMove(onLine, moveIndex);
	}

	private Cell decideOnAMove(AgentOnLineTrans aolt, int index) {
		if (aolt.getAgentAtLeft()) {
			if (aolt.getAgentAtLeftOnline()) {
				if (aolt.isImportantThanLeft())
					return foodToNest.get(index);
				else
					return agent.getMove();
			} else
				return agent.getMove(); // STAY PUT
		}
		if (aolt.getAgentAtRight()) {
			if (aolt.getAgentAtRightOnline()) {
				if (aolt.isImportantThanRight())
					return foodToNest.get(index);
				else
					return agent.getMove();
			} else
				return agent.getMove(); // STAY PUT
		} else if (aolt.getAgentAtAdjacent()) {
			reverseDirection();
			int mindex = getMoveIndex();
			if (mindex == foodToNest.size() || mindex < 0)
				reverseDirection();
			mindex = getMoveIndex();
			AgentOnLineTrans onLineAdj = new AgentOnLineTrans(agent.getMove(), foodToNest.get(mindex), snapshot, foodToNest);
			if (onLineAdj.getAgentAtLeft() || onLineAdj.getAgentAtRight() || aolt.getAgentAtAdjacent())
				return agent.getMove();
			if (onLineAdj.getAgentAtHead()) {
				if (onLineAdj.getImportantThanHead())
					return foodToNest.get(mindex);
				else
					return agent.getMove();
			}
		} else if (aolt.getAgentAtHead()) {
			if (aolt.getImportantThanHead())
				return foodToNest.get(index);
			else
				return agent.getMove();
		} else
			return foodToNest.get(index);
		return null;
	}

	private int getMoveIndex() {
		if (agent.getDirection().equals(TransDirection.FOOD_TO_NEST))
			return getCurrentIndex(foodToNest) + 1;
		else
			return getCurrentIndex(foodToNest) - 1;
	}

	private void reverseDirection() {
		if (agent.getDirection().equals(TransDirection.FOOD_TO_NEST))
			agent.setDirection(TransDirection.NEST_TO_FOOD);
		else
			agent.setDirection(TransDirection.FOOD_TO_NEST);
	}

	private int getCurrentIndex(List<Cell> path) {
		for (int i = 0; i < path.size(); i++) {
			if (path.get(i).equals(agent.getMove()))
				return i;
		}
		return 0;
	}

	public Agent getAgentWithMoveSet() {
		agent.setMove(nextMove);
		return agent;
	}

}
