package entities;

public class Collision {

	private int timeStep;
	private Cell cell;
	private int sector1;
	private int sector2;

	public Collision(int timeStep, Cell cell, int sector1, int sector2) {
		super();
		this.timeStep = timeStep;
		this.cell = cell;
		this.sector1 = sector1;
		this.sector2 = sector2;
	}

	public int getTimeStep() {
		return timeStep;
	}

	public void setTimeStep(int timeStep) {
		this.timeStep = timeStep;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public int getSector1() {
		return sector1;
	}

	public void setSector1(int sector1) {
		this.sector1 = sector1;
	}

	public int getSector2() {
		return sector2;
	}

	public void setSector2(int sector2) {
		this.sector2 = sector2;
	}
}
