package relaySimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.Operations;
import operations.PathOperations;
import patterns.Stripes;
import transHelpers.FoodPathTraversal;

import commHelpers.StripeCom;

import entities.Agent;
import entities.Cell;
import entities.Path;
import enums.Pattern;
import enums.Phase;
import enums.TransDirection;

public class StripeSimulation {

	private int gridSize;
	private int numAgents;
	private List<Path> paths;
	private List<Agent> agents;
	private int expStartTime;
	private Cell food;

	private Map<Integer, List<Cell>> map = new HashMap<Integer, List<Cell>>();
	private Boolean foodFound = false;
	private List<Cell> snapshot;

	private List<Cell> foodToNest;
	private final static int FOOD = 100;
	private int fini = 0;
	private int foodAmtAtSource = FOOD;
	private int foodAmtAtNest = 0;
	private Cell origin;

	public StripeSimulation(int gridSize, int numAgents, Cell food) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		this.food = food;
		this.paths = new Stripes(gridSize, numAgents).getPaths();
		this.origin = Operations.getOrigin(gridSize);
		if (numAgents % 2 == 1)
			this.expStartTime = gridSize - 1;
		else 
			this.expStartTime = ((numAgents - 1)* gridSize / numAgents) ;
		this.agents = new ArrayList<Agent>();
		this.foodToNest = PathOperations.getDeltaYPath(food, origin);
		foodToNest.add(origin);
		setAgents();
		startClock();
		for (Agent a : agents)
			a.setTransTime(fini);
		//Operations.displayCollection(agents);
	}

	public List<Agent> getAgents() {
		return agents;
	}

	private void startClock() {
		for (int clock = 0; !foodTransported(clock); clock++) {
			snapshot = new ArrayList<Cell>();
			for (int i = 0; i < numAgents; i++) {
				agents.set(i, getNextMove(agents.get(i), clock));
				snapshot.add(agents.get(i).getMove());
			}
			updateFoodFlagsAccTo(clock);
			checkPhaseCompletion(clock);
			exchangeFood();
			//printEntry(clock, snapshot);
			map.put(clock, snapshot);

		}
	}

	private boolean foodTransported(int clock) {
		if (foodAmtAtNest == FOOD) {
			fini = clock - 1;
			return true;
		} else
			return false;
	}

	public boolean foodLocKnownToEveryAgent() {
		for (Agent a : agents) {
			if (!a.getCommPhaseFlag().getCompleted())
				return false;
		}
		return true;
	}

	private void exchangeFood() {
		Agent holder;
		for (int i = 0; i < agents.size(); i++) {
			holder = agents.get(i);
			if (holder.getMove().equals(food) && holder.getCurrPhase().equals(Phase.TRANSPORTATION) && !holder.isFoodpossessed() && foodAmtAtSource >= 0) {
				foodAmtAtSource = foodAmtAtSource - 1;
				holder.setFoodInPossession(1);
				agents.set(i, holder);
			} else if (holder.getMove().equals(origin) && holder.getCurrPhase().equals(Phase.TRANSPORTATION) && holder.isFoodpossessed()) {
				foodAmtAtNest = foodAmtAtNest + 1;
				holder.setFoodInPossession(0);
				agents.set(i, holder);
			} else if (holder.getCurrPhase().equals(Phase.TRANSPORTATION) && holder.isFoodpossessed()) {
				int index = getCurrentIndex(holder);
				if (index != 0 && index < foodToNest.size()) {
					Cell n1 = foodToNest.get(index - 1);
					Cell n2 = foodToNest.get(index + 1);
					Cell closeToNest = (Operations.getDistance(n1, origin) < Operations.getDistance(n2, origin)) ? n1 : n2;
					int adj = anyAgentAt(closeToNest);
					if (adj >= 0) {
						Agent ex = agents.get(adj);
						if (!ex.isFoodpossessed()) {
							holder.setFoodInPossession(0);
							ex.setFoodInPossession(1);
							agents.set(adj, ex);
						}
					}
				}
				agents.set(i, holder);
			}
		}
	}

	private int anyAgentAt(Cell closeToNest) {
		for (Agent a : agents) {
			if (a.getMove().equals(closeToNest))
				return a.getId();
		}
		return -1;
	}

	private int getCurrentIndex(Agent ag) {
		for (int i = 0; i < foodToNest.size(); i++) {
			if (foodToNest.get(i).equals(ag.getMove()))
				return i;
		}
		return 0;
	}

	public void printEntry(int clock, List<Cell> snap) {
		System.out.print(clock + " [");
		for (int i = 0; i < snap.size(); i++) {
			System.out.print(i + ":" + agents.get(i).getCurrPhase().getAlias() + "|" + snap.get(i) + ",");
		}
		System.out.println("]");
	}

	private void checkPhaseCompletion(int clock) {

		for (Agent holder : agents) {

			if (holder.getCurrPhase().equals(Phase.INITIAL_DEPLOYMENT) && holder.getPath().getDepPath().size() == 0 && clock == expStartTime) {
				holder.setCurrPhase(Phase.EXPLORATION);
				holder.setInDePhaseFlag(true, clock);
			}

			if (holder.getCurrPhase().equals(Phase.EXPLORATION) && holder.getFoodLocAware()) {
				holder.setCurrPhase(Phase.COMMUNICATION);
				holder.setExPhaseFlag(true, clock);
			}

			if (holder.getCurrPhase().equals(Phase.COMMUNICATION)) {
				if (holder.getFoodLocator()) {
					int n1 = -1;
					int n2 = -1;
					if (holder.getId() == 0) {
						n1 = numAgents - 1;
						n2 = numAgents - 1;
					} else if (holder.getId() == numAgents - 1) {
						n1 = holder.getId() - 1;
						n2 = holder.getId() - 1;
					} else {
						n1 = holder.getId() - 1;
						n2 = holder.getId() + 1;
					}
					if (agents.get(n1).getCommPhaseFlag().getCompleted() && agents.get(n2).getCommPhaseFlag().getCompleted()) {
						holder.setCurrPhase(Phase.TRANSPORTATION);
						if (!holder.getCommPhaseFlag().getCompleted())
							holder.setCommPhaseFlag(true, clock);
					}
				} else {
					if (agents.get(holder.getTransmitTo()).getFoodLocAware()) {
						holder.setCurrPhase(Phase.TRANSPORTATION);
						if (!holder.getCommPhaseFlag().getCompleted())
							holder.setCommPhaseFlag(true, clock);
					}
				}
			}
		}
	}

	private void updateFoodFlagsAccTo(int clock) {
		if (!foodFound) {

			Agent holder;
			for (int i = 0; i < numAgents; i++) {
				holder = agents.get(i);
				if (holder.getMove().equals(food) && holder.getCurrPhase().equals(Phase.EXPLORATION)) {
					foodFound = true;
					holder.setFoodLocator(true);
					holder.setFoodLocAware(true);
					if (!holder.getExPhaseFlag().getCompleted())
						holder.setExPhaseFlag(true, clock);
					holder.setCurrPhase(Phase.COMMUNICATION);
					agents.set(i, holder);
					break;
				}
			}
		}

		List<Integer> aware = new ArrayList<Integer>();
		for (Agent a : agents) {
			if (a.getFoodLocAware())
				aware.add(a.getId());
		}

		if (aware.size() > 0) {
			for (Integer x : aware) {
				List<Integer> passTo = getAgentsInTheVicinity(x);
				if (passTo.size() > 0) {
					for (int j = 0; j < passTo.size(); j++) {
						int id = passTo.get(j);
						Agent ag = agents.get(id);
						ag.setFoodLocAware(true);
						if (!ag.getExPhaseFlag().getCompleted())
							ag.setExPhaseFlag(true, clock);
						ag.setCurrPhase(Phase.COMMUNICATION);
						int receivedFrom = x;
						ag.setReceivedFrom(receivedFrom, Pattern.STRIPES);
						agents.set(id, ag);
					}
				}
			}
		}

	}

	private List<Integer> getAgentsInTheVicinity(Integer aware) {
		List<Integer> list = new ArrayList<Integer>();
		for (int j = 0; j < numAgents; j++) {
			if (Operations.areNeighbours(agents.get(aware).getMove(), agents.get(j).getMove()))
				list.add(agents.get(j).getId());
		}
		return list;
	}

	private void setAgents() {
		List<Path> pathHolder = paths;
		for (Path p : pathHolder) {
			agents.add(new Agent(p.getId(), p, numAgents));
		}
		Collections.sort(agents);
	}

	private Agent getNextMove(Agent ag, int clock) {
		switch (ag.getCurrPhase()) {
		case INITIAL_DEPLOYMENT:
			return getNextIDmove(ag, clock);
		case EXPLORATION:
			return getNextEXmove(ag, clock);
		case COMMUNICATION:
			return getNextCOMMmove(ag, clock);
		case TRANSPORTATION:
			return getNextTRANSmove(ag, clock);
		case NULL:
			break;
		}
		return null;
	}

	private Agent getNextTRANSmove(Agent ag, int clock) {
		if (ag.isFoodpossessed())
			ag.setDirection(TransDirection.FOOD_TO_NEST);
		else
			ag.setDirection(TransDirection.NEST_TO_FOOD);
		ag = new FoodPathTraversal(ag, foodToNest, snapshot, gridSize).getAgentWithMoveSet();
		Cell move = ag.getMove();
		move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	private Agent getNextCOMMmove(Agent ag, int clock) {
		Cell move;
		if (ag.getPath().getPath().size() > 0) {
			move = ag.getPath().getPath().remove(0);
			move.setTimeStep(clock);
			ag.setMove(move);
			return ag;
		}
		if (ag.getCommPath() == null) {
			ag = new StripeCom(gridSize, numAgents, ag).getAgentWithCommPathSet();
		}

		if (ag.getCommPath().size() == 0) {
			ag = new StripeCom(gridSize, numAgents, ag).getAgentWithCommPathSet();
		}

		move = ag.getCommPath().remove(0);
		move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	private Agent getNextEXmove(Agent ag, int clock) {
		Cell move;
		if (ag.getPath().getPath().size() > 0) {
			move = ag.getPath().getPath().remove(0);
		} else {
			if (ag.getCommPath() == null || ag.getCommPath().size() == 0)
				ag = new StripeCom(gridSize, numAgents, ag).getAgentWithCommPathSet();
			move = ag.getCommPath().remove(0);
		}
		move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	private Agent getNextIDmove(Agent ag, int clock) {
		Cell move;
		if (ag.getPath().getDepPath().size() == 0)
			move = ag.getMove();
		else
			move = ag.getPath().getDepPath().remove(0);
		move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	public static void main(String[] args) {
		new StripeSimulation(128, 64, new Cell(50, 60));
	}
}
