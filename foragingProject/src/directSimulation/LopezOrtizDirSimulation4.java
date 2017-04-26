package directSimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.Operations;
import operations.PathOperations;
import patterns.LopezOrtiz_numAgents4;
import transHelpers.DirectTransport;

import commHelpers.LopezComm4;

import entities.Agent;
import entities.Cell;
import entities.Path;
import enums.Pattern;
import enums.Phase;

public class LopezOrtizDirSimulation4 {

	private int gridSize;
	private int numAgents;
	private List<Path> paths;
	private List<Agent> agents;
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

	public LopezOrtizDirSimulation4(int gridSize, int numAgents, Cell food) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		this.food = food;
		this.paths = new LopezOrtiz_numAgents4(gridSize).getPaths();
		//GeneralOperations.displayCollection(paths);
		this.origin = Operations.getOrigin(gridSize);
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
			if (holder.getCurrPhase().equals(Phase.EXPLORATION) && holder.getFoodLocAware()) {
				holder.setCurrPhase(Phase.COMMUNICATION);
				if (!holder.getExPhaseFlag().getCompleted())
					holder.setExPhaseFlag(true, clock);
			}

			if (holder.getCurrPhase().equals(Phase.COMMUNICATION) && holder.getCommPath() != null) {
				Agent transmit = agents.get(holder.getTransmitTo());
				if (transmit.getFoodLocAware()) {
					holder.setCurrPhase(Phase.TRANSPORTATION);
					if (!holder.getCommPhaseFlag().getCompleted())
						holder.setCommPhaseFlag(true, clock);
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
					holder.setReceivedFrom(holder.getId(), Pattern.LOPEZ_ORTIZ4);
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
						ag.setReceivedFrom(receivedFrom, Pattern.LOPEZ_ORTIZ4);
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
		List<Path> pathHolder = new ArrayList<Path>(paths);
		Agent holder;
		for (Path p : pathHolder) {
			holder = new Agent(p.getId(), p, numAgents);
			holder.setCurrPhase(Phase.EXPLORATION);
			holder.setInDePhaseFlag(true, 0);
			agents.add(holder);
		}
		Collections.sort(agents);
	}

	private Agent getNextMove(Agent ag, int clock) {
		switch (ag.getCurrPhase()) {
		case EXPLORATION:
			return getNextEXmove(ag, clock);
		case COMMUNICATION:
			return getNextCOMMmove(ag, clock);
		case TRANSPORTATION:
			return getNextTRANSmove(ag, clock);
		case NULL:
			break;
		case INITIAL_DEPLOYMENT:
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

		if (ag.getCommPath() == null) {
			ag = new LopezComm4(gridSize, ag, clock, paths).getAgentWithCommPathSet();
		}

		if (ag.getCommPath().size() == 0) {
			ag.setCurrPhase(Phase.TRANSPORTATION);
			if (!ag.getCommPhaseFlag().getCompleted())
				ag.setCommPhaseFlag(true, clock);
			return getNextTRANSmove(ag, clock);
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
			move = ag.getMove();
		}
		move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	public static void main(String[] args) {
		new LopezOrtizDirSimulation4(128, 4, new Cell(70, 85));
	}
}
