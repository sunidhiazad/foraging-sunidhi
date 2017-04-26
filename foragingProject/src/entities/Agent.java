package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enums.TransDirection;
import enums.Pattern;
import enums.Phase;

public class Agent implements Comparable<Agent> {

	private int id;
	private Path path;
	private Phase currPhase;

	private Boolean foodLocator = false;
	private Boolean foodLocAware;

	private int receivedFrom;
	private List<Integer> hierTransmitTo;
	private int transmitTo;

	private Completion inDePhaseFlag;
	private Completion exPhaseFlag;
	private Completion commPhaseFlag;

	private Cell move;
	private List<Cell> commPath;
	private List<Cell> transPath;

	private int numAgents;
	private TransDirection direction = TransDirection.NEST_TO_FOOD;

	private boolean foodpossessed = false;
	private int foodInPossession;

	private int transTime;

	public Agent(int id) {
		super();
		this.id = id;
	}

	public Agent(int id, Path path, int numAgents) {
		super();
		this.id = id;
		this.path = path;
		this.foodLocAware = false;
		this.currPhase = Phase.INITIAL_DEPLOYMENT;
		this.inDePhaseFlag = new Completion(false, -1);
		this.exPhaseFlag = new Completion(false, -1);
		this.commPhaseFlag = new Completion(false, -1);
		this.numAgents = numAgents;
		this.hierTransmitTo = new ArrayList<Integer>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Boolean getFoodLocAware() {
		return foodLocAware;
	}

	public void setFoodLocAware(Boolean foodLocAware) {
		this.foodLocAware = foodLocAware;
	}

	public Completion getInDePhaseFlag() {
		return inDePhaseFlag;
	}

	public void setInDePhaseFlag(Boolean flag, int clock) {
		this.inDePhaseFlag.setCompleted(flag);
		this.inDePhaseFlag.setTimeStep(clock);
	}

	public Completion getExPhaseFlag() {
		return exPhaseFlag;
	}

	public void setExPhaseFlag(Boolean flag, int clock) {
		this.exPhaseFlag.setCompleted(flag);
		this.exPhaseFlag.setTimeStep(clock);
	}

	public Completion getCommPhaseFlag() {
		return commPhaseFlag;
	}

	public void setCommPhaseFlag(Boolean flag, int clock) {
		this.commPhaseFlag.setCompleted(flag);
		this.commPhaseFlag.setTimeStep(clock);
	}

	public Phase getCurrPhase() {
		return currPhase;
	}

	public void setCurrPhase(Phase currPhase) {
		this.currPhase = currPhase;
	}

	public Boolean getFoodLocator() {
		return foodLocator;
	}

	public void setFoodLocator(Boolean foodLocator) {
		this.foodLocator = foodLocator;
	}

	public Cell getMove() {
		return move;
	}

	public void setMove(Cell move) {
		this.move = move;
	}

	public List<Cell> getCommPath() {
		return commPath;
	}

	public void setCommPath(List<Cell> commPath) {
		this.commPath = commPath;
	}

	public int getReceivedFrom() {
		return receivedFrom;
	}

	public void setReceivedFrom(int receivedFrom, Pattern pattern) {
		this.receivedFrom = receivedFrom;
		if (pattern.equals(Pattern.SECTORS)) {
			if (id == 0 && receivedFrom == numAgents - 1)
				setTransmitTo(1);
			else if (id == 0 && receivedFrom == 1)
				setTransmitTo(numAgents - 1);
			else if (id == numAgents - 1 && receivedFrom == 0)
				setTransmitTo(numAgents - 2);
			else if (id == numAgents - 1 && receivedFrom == numAgents - 2)
				setTransmitTo(0);
			else if (receivedFrom == id + 1)
				setTransmitTo(id - 1);
			else if (receivedFrom == id - 1)
				setTransmitTo(id + 1);
		} else if (pattern.equals(Pattern.STRIPES)) {
			if (id == 0 && receivedFrom == 1)
				setTransmitTo(0);
			else if (id == numAgents - 1 && receivedFrom == numAgents - 2)
				setTransmitTo(numAgents - 1);
			else if (receivedFrom == id + 1)
				setTransmitTo(id - 1);
			else if (receivedFrom == id - 1)
				setTransmitTo(id + 1);
		} else if (pattern.equals(Pattern.LOPEZ_ORTIZ4)) {
			if (getFoodLocator()) {
				if (id == 0)
					setTransmitTo(1);
				else if (id == numAgents - 1)
					setTransmitTo(0);
				else
					setTransmitTo(id + 1);
			} else {
				if (id == 0 && receivedFrom == 1)
					setTransmitTo(3);
				else if (id == 0 && receivedFrom == 3)
					setTransmitTo(1);
				else if (id == numAgents - 1 && receivedFrom == numAgents - 2)
					setTransmitTo(0);
				else if (id == numAgents - 1 && receivedFrom == 0)
					setTransmitTo(numAgents - 2);
				else if (receivedFrom == id + 1)
					setTransmitTo(id - 1);
				else if (receivedFrom == id - 1)
					setTransmitTo(id + 1);
			}
		} else if (pattern.equals(Pattern.LOPEZ_ORTIZ8)) {
			if (getFoodLocator()) {
				if (id == 0)
					setTransmitTo(1);
				else if (id == numAgents - 1)
					setTransmitTo(0);
				else
					setTransmitTo(id + 1);
			} else {
				if (id == 0 && receivedFrom == 1)
					setTransmitTo(numAgents - 1);
				else if (id == 0 && receivedFrom == numAgents - 1)
					setTransmitTo(1);
				else if (id == numAgents - 1 && receivedFrom == numAgents - 2)
					setTransmitTo(0);
				else if (id == numAgents - 1 && receivedFrom == 0)
					setTransmitTo(numAgents - 2);
				else if (receivedFrom == id + 1)
					setTransmitTo(id - 1);
				else if (receivedFrom == id - 1)
					setTransmitTo(id + 1);
			}
		} else if (pattern.equals(Pattern.ADVANCED_SECTORS)) {
			switch (id) {
			case 0:
				setHierTransmitTo(Arrays.asList(2, 6));
				setTransmitTo(1);
				break;
			case 1:
				setHierTransmitTo(Arrays.asList(3, 7));
				setTransmitTo(0);
				break;
			case 2:
				setHierTransmitTo(Arrays.asList(0, 4));
				setTransmitTo(3);
				break;
			case 3:
				setHierTransmitTo(Arrays.asList(1, 5));
				setTransmitTo(2);
				break;
			case 4:
				setHierTransmitTo(Arrays.asList(2, 6));
				setTransmitTo(5);
				break;
			case 5:
				setHierTransmitTo(Arrays.asList(3, 7));
				setTransmitTo(4);
				break;
			case 6:
				setHierTransmitTo(Arrays.asList(0, 4));
				setTransmitTo(7);
				break;
			case 7:
				setHierTransmitTo(Arrays.asList(1, 5));
				setTransmitTo(6);
				break;
			}
		}
	}

	public int getTransmitTo() {
		return transmitTo;
	}

	public void setTransmitTo(int transmitTo) {
		this.transmitTo = transmitTo;
	}

	public List<Cell> getTransPath() {
		return transPath;
	}

	public void setTransPath(List<Cell> transPath) {
		this.transPath = transPath;
	}

	public List<Integer> getHierTransmitTo() {
		return hierTransmitTo;
	}

	public void setHierTransmitTo(List<Integer> hierTransmitTo) {
		this.hierTransmitTo = hierTransmitTo;
	}

	public TransDirection getDirection() {
		return direction;
	}

	public void setDirection(TransDirection direction) {
		this.direction = direction;
	}

	public boolean isFoodpossessed() {
		return foodpossessed;
	}

	public void setFoodpossessed(boolean foodpossessed) {
		this.foodpossessed = foodpossessed;
	}

	public int getFoodInPossession() {
		return foodInPossession;
	}

	public void setFoodInPossession(int foodInPossession) {
		this.foodInPossession = foodInPossession;
		if (foodInPossession > 0)
			setFoodpossessed(true);
		else
			setFoodpossessed(false);
	}

	public int getTransTime() {
		return transTime;
	}

	public void setTransTime(int transTime) {
		this.transTime = transTime;
	}

	@Override
	public int compareTo(Agent a) {
		if (this.getId() > a.getId())
			return 1;
		else if (this.getId() < a.getId())
			return -1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", received=" + receivedFrom + ", transmitTo=" + transmitTo + ", inDePhaseFlag=" + inDePhaseFlag.getTimeStep() + ", exPhaseFlag=" + exPhaseFlag.getTimeStep() + ", commPhaseFlag=" + commPhaseFlag.getTimeStep() + ", transport=" + transTime + "]";
	}

	// @Override
	// public String toString() {
	// return "Agent [id=" + id + ", foodLocator=" + foodLocator +
	// ", foodLocAware=" + foodLocAware + ", receivedFrom=" + receivedFrom +
	// ", hierTransmitTo=" + hierTransmitTo + ", transmitTo=" + transmitTo +
	// "]";
	// }
	public class Completion {
		Boolean completed;
		int timeStep;

		public Completion() {
			super();
		}

		public Completion(Boolean completed, int timeStep) {
			super();
			this.completed = completed;
			this.timeStep = timeStep;
		}

		public Boolean getCompleted() {
			return completed;
		}

		public void setCompleted(Boolean completed) {
			this.completed = completed;
		}

		public int getTimeStep() {
			return timeStep;
		}

		public void setTimeStep(int timeStep) {
			this.timeStep = timeStep;
		}

		@Override
		public String toString() {
			return "Completion [completed=" + completed + ", timeStep=" + timeStep + "]";
		}

	}

}
