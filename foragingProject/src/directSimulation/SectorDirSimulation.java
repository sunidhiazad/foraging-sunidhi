package directSimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import operations.Operations;
import operations.PathOperations;
import patterns.SectorWiseDivision;
import transHelpers.DirectTransport;

import commHelpers.SecCom;

import entities.Agent;
import entities.Cell;
import entities.Path;
import enums.Pattern;
import enums.Phase;

public class SectorDirSimulation {

	private int gridSize;
	private int numAgents;
	private List<Path> paths;
	private List<Agent> agents;

	private Cell food;
	private int clock;
	private List<Cell> snapshot;
	private Map<Integer, List<Cell>> map = new HashMap<Integer, List<Cell>>();
	private Boolean foodFound = false;

	private List<Cell> foodToNest;
	private final static int FOOD = 100;
	private int fini = 0;
	private int foodAmtAtSource = FOOD;
	private int foodAmtAtNest = 0;
	private Cell origin;

	public SectorDirSimulation(int gridSize, int numAgents, Cell food) {
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		this.food = food;
		this.paths = new SectorWiseDivision(gridSize, numAgents).getPaths();
		this.origin = Operations.getOrigin(gridSize);
		this.foodToNest = PathOperations.getDeltaYPath(food, origin);
		foodToNest.add(origin);
		this.agents = new ArrayList<Agent>();
		setAgents();
		startClock();
		for (Agent a : agents)
			a.setTransTime(fini);
		//GridOperations.displayCollection(agents);
	}

	public List<Agent> getAgents() {
		return agents;
	}

	private void startClock() {
		for (clock = 0; !foodTransported(clock); clock++) {
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
		Agent holder;
		for (int i = 0; i < numAgents; i++) {
			holder = agents.get(i);

			if (holder.getCurrPhase().equals(Phase.INITIAL_DEPLOYMENT) && holder.getPath().getDepPath().size() == 0) {
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
						n2 = 1;
					} else if (holder.getId() == numAgents - 1) {
						n1 = holder.getId() - 1;
						n2 = 0;
					} else {
						n1 = holder.getId() - 1;
						n2 = holder.getId() + 1;
					}
					if (agents.get(n1).getCommPhaseFlag().getCompleted() && agents.get(n2).getCommPhaseFlag().getCompleted()) {
						holder.setCurrPhase(Phase.TRANSPORTATION);
						if (!holder.getCommPhaseFlag().getCompleted())
							holder.setCommPhaseFlag(true, clock);
					}
				}
			}
			if (holder.getCurrPhase().equals(Phase.COMMUNICATION) && holder.getCommPath() != null && holder.getCommPath().size() == 0) {
				if (holder.getFoodLocator()) {
					int n1 = -1;
					int n2 = -1;
					if (holder.getId() == 0) {
						n1 = numAgents - 1;
						n2 = 1;
					} else if (holder.getId() == numAgents - 1) {
						n1 = holder.getId() - 1;
						n2 = 0;
					} else {
						n1 = holder.getId() - 1;
						n2 = holder.getId() + 1;
					}
					if (agents.get(n1).getFoodLocAware() && agents.get(n2).getFoodLocAware()) {
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

			agents.set(i, holder);
		}
	}

	private void updateFoodFlagsAccTo(int clock) {

		// if the food is not discovered yet, check the current snapshot to see
		// if an agent has stumbled upon the food. If yes, set the
		// foodLocatorFlag & FoodLocAwareFlag.
		if (!foodFound) {

			Agent holder;
			for (int i = 0; i < numAgents; i++) {
				holder = agents.get(i);
				if (holder.getMove().equals(food)) {
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
		for (int i = 0; i < numAgents; i++) {
			if (agents.get(i).getFoodLocAware())
				aware.add(agents.get(i).getId());
		}

		if (aware.size() > 0) {
			for (int i = 0; i < aware.size(); i++) {
				List<Integer> passTo = getAgentsInTheVicinity(aware.get(i));
				if (passTo.size() > 0) {
					for (int j = 0; j < passTo.size(); j++) {
						int id = passTo.get(j);
						Agent ag = agents.get(id);
						ag.setFoodLocAware(true);
						if (!ag.getExPhaseFlag().getCompleted())
							ag.setExPhaseFlag(true, clock);
						ag.setCurrPhase(Phase.COMMUNICATION);
						int receivedFrom = aware.get(i);
						ag.setReceivedFrom(receivedFrom, Pattern.SECTORS);
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

	public Cell getFoodDiscoverer() {
		Cell locator = new Cell();
		List<Cell> expPath;
		List<Cell> foodLocators = new ArrayList<Cell>();
		for (Path p : paths) {
			expPath = p.getPath();
			for (Cell c : expPath) {
				if (c.equals(food)) {
					c.setPathid(p.getId());
					foodLocators.add(c);
				}
			}
		}
		if (foodLocators.size() == 1)
			locator = foodLocators.get(0);
		else {
			Cell k = foodLocators.get(0);
			for (Cell c : foodLocators) {
				if (c.getTimeStep() < k.getTimeStep()) {
					k = c;
				}
			}
			locator = k;
		}
		return locator;
	}

	private void setAgents() {
		List<Path> pathHolder = new ArrayList<Path>(paths);
		for (Path p : pathHolder) {
			agents.add(new Agent(p.getId(), p, numAgents));
		}
		Collections.sort(agents);
	}

	public boolean foodLocKnownToEveryAgent() {
		for (Agent a : agents) {
			if (!a.getCommPhaseFlag().getCompleted())
				return false;
		}
		return true;
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
		if (ag.getCommPath() == null) {
			ag = new SecCom(gridSize, numAgents, ag, food, clock, paths).getAgentWithCommPathSet();
		}
		if (ag.getCommPath().size() == 0) {
			Cell move = ag.getMove();
			move.setTimeStep(clock);
			ag.setMove(move);
			return ag;
		}
		Cell move = ag.getCommPath().remove(0);
		move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	private Agent getNextEXmove(Agent ag, int clock) {
		Cell move;
		if (ag.getPath().getPath().size() == 0)
			move = ag.getPath().getLast();
		else
			move = ag.getPath().getPath().remove(0);
		move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	private Agent getNextIDmove(Agent ag, int clock) {
		Cell move = ag.getPath().getDepPath().remove(0);
		move.setTimeStep(clock);
		ag.setMove(move);
		return ag;
	}

	public static void main(String[] args) {
		new SectorDirSimulation(128, 16, new Cell(11, 45));
	}
}
