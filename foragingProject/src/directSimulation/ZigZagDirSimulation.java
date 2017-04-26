package directSimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.Operations;
import operations.PathOperations;
import patterns.ZigZag;
import transHelpers.DirectTransport;

import commHelpers.HierComm;
import commHelpers.HierTransmission;

import entities.Agent;
import entities.Cell;
import entities.Path;
import enums.Pattern;
import enums.Phase;

public class ZigZagDirSimulation {

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

	public ZigZagDirSimulation(int gridSize, int numAgents, Cell food) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		this.food = food;
		this.paths = new ZigZag(gridSize, numAgents).getPaths();
		this.origin = Operations.getOrigin(gridSize);
		if (numAgents == 4)
			this.expStartTime = 1;
		else
			this.expStartTime = 3 * gridSize / 2 - 2 * gridSize / (int) Math.sqrt(numAgents);
		this.agents = new ArrayList<Agent>();
		this.foodToNest = PathOperations.getDeltaYPath(food, origin);
		foodToNest.add(origin);
		setAgents();
		startClock();
		for (Agent a : agents)
			a.setTransTime(fini);
		//GridOperations.displayCollection(agents);
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

	public List<Agent> getAgents() {
		return agents;
	}

	private void exchangeFood() {
		Agent holder;
		for (int i = 0; i < agents.size(); i++) {
			holder = agents.get(i);
			if (holder.getMove().equals(food) && holder.getCurrPhase().equals(Phase.TRANSPORTATION) && !holder.isFoodpossessed() && foodAmtAtSource >= 0) {
				foodAmtAtSource = foodAmtAtSource - 1;
				holder.setFoodInPossession(1);
			} else if (holder.getMove().equals(origin) && holder.getCurrPhase().equals(Phase.TRANSPORTATION) && holder.isFoodpossessed()) {
				foodAmtAtNest = foodAmtAtNest + 1;
				holder.setFoodInPossession(0);
			}
			agents.set(i, holder);
		}
	}

	public void printEntry(int clock, List<Cell> snapshot) {
		System.out.print(clock + " [");
		for (int i = 0; i < snapshot.size(); i++) {
			System.out.print(i + ":" + agents.get(i).getCurrPhase().getAlias() + "|" + snapshot.get(i) + ",");
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
				if (communicatedToAll(holder)) {
					holder.setCurrPhase(Phase.TRANSPORTATION);
					if (!holder.getCommPhaseFlag().getCompleted())
						holder.setCommPhaseFlag(true, clock);
				}
			}
		}
	}

	private boolean communicatedToAll(Agent holder) {
		for (int i : holder.getHierTransmitTo()) {
			if (!agents.get(i).getFoodLocAware())
				return false;
		}
		return true;
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
					holder = new HierComm(gridSize, numAgents, holder, food, Pattern.ZIG_ZAG).getAgentWithCommPathSet();
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
					// x transmits to y
					for (Integer y : passTo) {
						Agent ag = agents.get(y);
						ag.setFoodLocAware(true);
						if (!ag.getExPhaseFlag().getCompleted())
							ag.setExPhaseFlag(true, clock);
						ag.setCurrPhase(Phase.COMMUNICATION);
						ag.setReceivedFrom(x, Pattern.ZIG_ZAG);
						ag = new HierTransmission(ag, paths).getAgentWithTransmissionSet();
						agents.set(y, ag);
						List<Integer> tlist = agents.get(x).getHierTransmitTo();
						tlist.remove(new Integer(y));
						agents.get(x).setHierTransmitTo(tlist);
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
		ag = new DirectTransport(ag, snapshot, food, gridSize).getNextMove();
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
			ag = new HierComm(gridSize, numAgents, ag, food, Pattern.ZIG_ZAG).getAgentWithCommPathSet();
		}

		if (ag.getCommPath().size() == 0) {
			ag = new HierComm(gridSize, numAgents, ag, food, Pattern.ZIG_ZAG).getAgentWithCommPathSet();
		}

		if (ag.getCommPath().size() == 0) {
			return getNextTRANSmove(ag, clock);
		}
		move = ag.getCommPath().remove(0);
		if (move != null)
			move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	private Agent getNextEXmove(Agent ag, int clock) {
		Cell move;
		if (ag.getPath().getPath().size() > 0)
			move = ag.getPath().getPath().remove(0);
		else
			move = ag.getMove();

		move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	private Agent getNextIDmove(Agent ag, int clock) {
		Cell move;
		if (ag.getPath().getDepPath().size() == 0) {
			if (clock < expStartTime)
				move = ag.getMove();
			else
				return getNextEXmove(ag, clock);
		}

		else
			move = ag.getPath().getDepPath().remove(0);
		move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	public boolean foodLocKnownToEveryAgent() {
		for (Agent a : agents) {
			if (!a.getCommPhaseFlag().getCompleted())
				return false;
		}
		return true;
	}

	private void setAgents() {
		for (Path p : paths) {
			agents.add(new Agent(p.getId(), p, numAgents));
		}
		Collections.sort(agents);
	}

	public static void main(String[] args) {
		new ZigZagDirSimulation(128, 16, new Cell(31, 45));
	}
}
