package entities;

import enums.Pattern;

public class Parameters {

	private int id;
	// inputs
	private int gridSize;
	private int numAgents;
	private int pattern;
	private Pattern pattrn;
	private int foodX;
	private int foodY;
	private int separation;
	// outputs
	private int initDepTS;
	private int foodDiscoveryTS;
	private int firstCommTS;
	private int firstTransTS;
	private int foodTransportTS;

	public Parameters() {
		super();
	}

	public Parameters(int id, int gridSize, int numAgents, int pattern, int foodX, int foodY) {
		super();
		this.id = id;
		this.gridSize = gridSize;
		this.numAgents = numAgents;
		this.pattern = pattern;
		this.foodX = foodX;
		this.foodY = foodY;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public int getNumAgents() {
		return numAgents;
	}

	public void setNumAgents(int numAgents) {
		this.numAgents = numAgents;
	}

	public int getPattern() {
		return pattern;
	}

	public void setPattern(int pattern) {
		this.pattern = pattern;
		setPattrn(Pattern.get(pattern));
	}

	public Pattern getPattrn() {
		return pattrn;
	}

	public void setPattrn(Pattern pattrn) {
		this.pattrn = pattrn;
	}

	public int getFoodX() {
		return foodX;
	}

	public void setFoodX(int foodX) {
		this.foodX = foodX;
	}

	public int getFoodY() {
		return foodY;
	}

	public void setFoodY(int foodY) {
		this.foodY = foodY;
	}

	public int getInitDepTS() {
		return initDepTS;
	}

	public void setInitDepTS(int initDepTS) {
		this.initDepTS = initDepTS;
	}

	public int getFoodDiscoveryTS() {
		return foodDiscoveryTS;
	}

	public void setFoodDiscoveryTS(int foodDiscoveryTS) {
		this.foodDiscoveryTS = foodDiscoveryTS;
	}

	public int getFirstCommTS() {
		return firstCommTS;
	}

	public void setFirstCommTS(int firstCommTS) {
		this.firstCommTS = firstCommTS;
	}

	public int getFirstTransTS() {
		return firstTransTS;
	}

	public void setFirstTransTS(int firstTransTS) {
		this.firstTransTS = firstTransTS;
	}

	public int getFoodTransportTS() {
		return foodTransportTS;
	}

	public void setFoodTransportTS(int foodTransportTS) {
		this.foodTransportTS = foodTransportTS;
	}

	public int getSeparation() {
		return separation;
	}

	public void setSeparation(int separation) {
		this.separation = separation;
	}

	private String getSep(int separation) {
		if (separation == 1)
			return "0-20";
		if (separation == 2)
			return "20-40";
		if (separation == 3)
			return "40-60";
		if (separation == 4)
			return "60-80";
		if (separation == 5)
			return "80-100";
		if (separation == 6)
			return "0-100";
		return "";
	}

	@Override
	public String toString() {
		return id + "|" + getSep(separation) + "|" + gridSize + "|" + numAgents + "|" + pattrn + "|" + foodX + "|" + foodY + "|" + initDepTS + "|" + foodDiscoveryTS + "|" + firstCommTS + "|" + firstTransTS + "|" + foodTransportTS;
	}

}
